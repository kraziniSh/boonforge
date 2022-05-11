/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

import com.github.l9cro1xx.boonforge.Modpack
import com.github.l9cro1xx.boonforge.serializables.ModpackManifest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

const val SAMPLE_MODPACK_NAME: String = "SampleModpack"
const val SAMPLE_MODPACK_MANIFEST: String = "$SAMPLE_MODPACK_NAME/manifest.json"
const val SAMPLE_MODPACK_OVERRIDES: String = "$SAMPLE_MODPACK_NAME/overrides"

internal class ModpackTest {

    @Test
    fun downloadModAsync(): Unit = runBlocking {
        // TODO("Equals assertion not implemented")

        val modpackManifest = this::class.java.classLoader.getResource(
            SAMPLE_MODPACK_MANIFEST
        )

        withContext(Dispatchers.IO) {
            val manifestBR = modpackManifest?.openStream()?.bufferedReader()
                ?: throw FileNotFoundException("Could not get Sample Modpack")

            val modpackManifestDecoded = manifestBR.use { _manifestBR ->
                Json.decodeFromString<ModpackManifest>(_manifestBR.readText())
            }

            val mockpack = Modpack(modpackManifestDecoded)

            val progressChannel = Channel<Pair<String, Long>>(Channel.CONFLATED)

            mockpack.downloadModAsync(
                mockpack.mods.random(),
                "boonforge-test",
                progressChannel
            )

            /*
            assertEquals(

            )
             */
        }
    }

    @Test
    fun downloadModAsyncWithExistingMod() {
        TODO("Unfinished because of project abandonment.")
    }

    @Test
    fun downloadModAsyncWithExistingModName() {
        TODO("Unfinished because of project abandonment.")
    }
}
