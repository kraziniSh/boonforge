/*
 Copyright (c) 2022 L9CRO1XX
 
 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge

import com.github.l9cro1xx.boonforge.exceptions.URLNullContentException
import com.github.l9cro1xx.boonforge.exceptions.URLNullDownloadLink
import com.github.l9cro1xx.boonforge.serializables.ModManifest
import com.github.l9cro1xx.boonforge.serializables.ModpackManifest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.headersContentLength
import java.io.FileOutputStream
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val DEFAULT_OUTPUT_NAME = "boonforge-output"
private const val API_URL = "https://addons-ecs.forgesvc.net/api/v2/addon/^projectId/file/^fileId/"
private const val API_DOWNLOAD_URL =
    "https://addons-ecs.forgesvc.net/api/v2/addon/^projectId/file/^fileId/download-url/"

private val httpClient = OkHttpClient()

// Could need to be put somewhere else
private fun newCallFromUrl(url: String): Call {
    Request.Builder()
        .url(url)
        .build()
        .let { request ->
            return httpClient.newCall(request)
        }
}

/**
 * Represents a CurseForge modpack.
 *
 * CurseForge modpacks contain two main elements:
 * * [ModpackManifest] – (manifest.json) information about the modpack, such as
 * IDs of its files;
 * * Overrides – (overrides/) a directory containing files and assets, such as
 * configuration files or even some mods.
 *
 * **Note**: you will need to build [Mod]s to use methods here.
 *
 * @param[manifest] The modpack's manifest.
 * @property[mods] The modpack's mods' project IDs and file IDs.
 * @property[overrides] The name of the `overrides` directory.
 * _Almost always `"overrides"`._
 * @constructor Symbolizes a modpack, using information from its manifest.
 * @author L9CRO1XX
 * @since 0.1.0
 */
class Modpack(manifest: ModpackManifest) {
    val mods: List<Mod> = manifest.files
    val overrides: String = manifest.overrides

    /**
     * Gets a mod's [ModManifest] synchronously, which contains a lot of
     * information about it.
     *
     * Information can be its name, its version, or even its download link
     * (use [getModDownloadUrl] instead).
     *
     * To download a mod, use [downloadModAsync] instead.
     *
     * @param[mod] The mod itself, containing its project ID and file ID.
     * @return The mod's [ModManifest], containing information about it.
     * @throws[URLNullContentException] If we couldn't find the mod.
     * @author L9CRO1XX
     * @since 0.1.0
     * @see[getModDownloadUrl]
     * @see[downloadModAsync]
     */
    fun getModManifest(mod: Mod): ModManifest {
        val projectId = mod.projectId
        val fileId = mod.fileId

        val call = newCallFromUrl(
            API_URL
                .replace("^projectId", "$projectId")
                .replace("^fileId", "$fileId")
        )

        call.execute().use { response ->
            val bufferedSource = response.body?.source()
            // TODO Move to strings resource bundle?
                ?: throw URLNullContentException("Could not get file")

            bufferedSource.use {
                val modFileEncoded: String = it.readUtf8()
                return Json.decodeFromString(modFileEncoded)
            }
        }
    }

    /**
     * Returns the full name of a mod.
     *
     * The full name can include its version, for example.
     *
     * @param[mod] The mod itself, containing its project ID and file ID.
     * @return The full name of the mod, as a [String].
     * @throws[URLNullContentException] If we couldn't find the mod.
     * @author L9CRO1XX
     * @since 0.1.0
     */
    fun getModName(mod: Mod): String {
        return getModManifest(mod).fileName
    }

    /**
     * Gets a mod's direct download URL, synchronously.
     *
     * @param[mod] The mod itself, containing its project ID and file ID.
     * @return The mod's direct download URL, as a [String].
     * @throws[URLNullContentException] If we could not get the download URL.
     * @author L9CRO1XX
     * @since 0.1.0
     */
    fun getModDownloadUrl(mod: Mod): String {
        val projectId = mod.projectId
        val fileId = mod.fileId

        val call = newCallFromUrl(
            API_DOWNLOAD_URL
                .replace("^projectId", "$projectId")
                .replace("^fileId", "$fileId")
        )

        call.execute().use { response ->
            return response.body?.string()
                ?: throw URLNullContentException("Mod file download URL is null")
        }
    }

    // TODO Maybe put the next two funs content into a "ModDownloader" class.
    /**
     * Downloads a mod, saves it to a named [output] in the current
     * directory, then returns a [Path] to the mod.
     *
     * If the mod is already on disk and that its content is the same as on the
     * web, then it will return the [Path] to it.
     * If the content is different, then this fun
     * will handle the overwrite prompt.
     *
     * @param[mod] The mod itself, containing its project ID and file ID.
     * @param[output] Name of the output directory.
     * @param[progressChannel] A **CONFLATED** channel used for transmitting
     * progress status in percents (%). Use it for retrieving download/saving
     * progress. Optional, can be ignored and not created.
     * @return [Path] to the downloaded mod (even if it is already on disk)
     * @throws[URLNullDownloadLink] If we couldn't get the download URL.
     * @throws[URLNullContentException] If we couldn't get the content.
     * @author L9CRO1XX
     * @since 0.1.0
     */
    suspend fun downloadModAsync(
        mod: Mod,
        output: String = DEFAULT_OUTPUT_NAME,
        progressChannel: Channel<Pair<String, Long>>? = null, // MUST BE CONFLATED!!
    ) = coroutineScope {
        launch(Dispatchers.Default) {
            val modName = getModName(mod)
            val downloadUrl = getModDownloadUrl(mod)

            val call = newCallFromUrl(downloadUrl)

            call.execute().use { response ->
                val potentialMod = Path("$output/$modName.jar")
                if (potentialMod.exists()) {
                    /* TODO File already existing check:
                    if (Response content is identical to mod on disk content) {
                        Return path of mod on disk
                    } else {
                        Prompt overwrite
                    }
                    */
                }

                val totalBytes = response.headersContentLength()

                val modBufferedInputStream =
                    response.body?.byteStream()?.buffered(DEFAULT_BUFFER_SIZE)
                        ?: throw URLNullContentException("Mod data could not be retrieved")
                val modBufferedOutputStream =
                    FileOutputStream("$output/$modName.jar").buffered(DEFAULT_BUFFER_SIZE)

                withContext(Dispatchers.IO) {
                    modBufferedInputStream.use { bufferedInputStream ->
                        modBufferedOutputStream.use { bufferedOutputStream ->
                            while (bufferedInputStream.available() > 0) {
                                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                                val bytesRead: Int

                                bufferedInputStream.read(buffer)
                                    .let {
                                        bytesRead = it
                                    }

                                bufferedOutputStream.write(buffer)

                                progressChannel?.send(
                                    Pair(
                                        "Saving $modName...",
                                        (bytesRead / totalBytes) * 100 // Progress in percents
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Downloads all of this modpack's mods, and saves them to a named
     * [output] in the current directory.
     *
     * Existing mods on disk with the same content as on the web will be
     * skipped. Those with different content will be asked for overwriting.
     *
     * @param[output] Name of the output directory. Optional and defaults to
     * "boonforge-output".
     * @param[workers] Maximum number of simultaneous downloads. Optional and
     * defaults to the JVM's available processors _(usually the amount of
     * threads of the machine)_.
     * @param[progressChannel] A **CONFLATED** channel used for transmitting
     * progress status in percents (%). Use it for retrieving download/saving
     * progress. Optional, can be ignored and not created.
     * @throws[URLNullDownloadLink] If we couldn't get the download URL.
     * @throws[URLNullContentException] If we couldn't get the content.
     * @author L9CRO1XX
     * @since 0.1.0
     */
    suspend fun downloadModsAsync(
        output: String = DEFAULT_OUTPUT_NAME,
        workers: Int = Runtime.getRuntime().availableProcessors(),
        progressChannel: Channel<Pair<String, Long>>? = null, // MUST BE CONFLATED!!
    ): Unit = coroutineScope {
        TODO("Unfinished because of project abandonment.")
    }
}
