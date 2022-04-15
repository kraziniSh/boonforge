package com.github.l9cro1xx.boonforge

import com.github.l9cro1xx.boonforge.jsonSerializers.AddonFile
import com.github.l9cro1xx.boonforge.jsonSerializers.Manifest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

private val httpClient = OkHttpClient()

fun getAddonFileInfo(addonID: Int, fileID: Int): AddonFile? {
    val request = Request.Builder()
        .url("https://addons-ecs.forgesvc.net/api/v2/addon/$addonID/file/$fileID/")
        .build()

    val response = httpClient.newCall(request).execute()
    response.use {
        val body = it.body
        if (body != null) {
            return Json.decodeFromString<AddonFile>(body.string())
        } else {
            return null // TODO Maybe throw here?
        }
    }
}

fun getFileName(addonID: Int, fileID: Int): String? {
    return getAddonFileInfo(addonID, fileID)?.displayName
}

fun getFileDownloadUrl(addonID: Int, fileID: Int): String? {
    return getAddonFileInfo(addonID, fileID)?.downloadUrl
}

/**
 * Downloads a modpack file, and saves it at the given [output].
 *
 * @param [addonID] the addon's ID, e.q. mod project (found in the modpack's
 * manifest).
 * @param [fileID] an addon's file ID, e.q. mod file (found in the modpack's
 * manifest).
 * @param [output] any path of the desired output.
 *
 * @return whether we successfully downloaded the file.
 *
 * @author L9CRO1XX
 *
 * @since 0.0.1
 *
 * @see downloadFiles
 */
fun downloadFile(addonID: Int, fileID: Int, output: String): Boolean {
    val downloadUrl = getFileDownloadUrl(addonID, fileID)

    if (downloadUrl != null) {
        val request = Request.Builder()
            .url(downloadUrl)
            .build()

        val response = httpClient.newCall(request).execute()
        response.use {
            val fileName = getFileName(addonID, fileID)

            val jarIS = it.body?.byteStream()

            val jar = File("$output/$fileName")
            jar.mkdirs()

            val jarOS = jar.outputStream()

            jarIS.use { _jarIS ->
                jarOS.use { _jarOS ->
                    _jarIS?.transferTo(_jarOS)
                    return true
                }
            }
        }
    } else {
        return false
    }
}

/**
 *
 */
fun downloadFiles(manifest: Manifest, output: String) {
    downloadFile(389665, 3247154, output) // TODO Pls...
}
