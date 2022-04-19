/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain
 one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.l9cro1xx.boonforge.downloadFiles
import com.github.l9cro1xx.boonforge.getResourceString
import com.github.l9cro1xx.boonforge.jsonSerializers.Manifest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.lingala.zip4j.ZipFile
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.system.exitProcess

class DownloadCommand: CliktCommand(
    name = getResourceString("DownloadCommand.Name"),
    help = getResourceString("DownloadCommand.Help")
) {
    private val input by argument(help = getResourceString("DownloadCommand.Arguments.Input"))
    private val output by argument(help = getResourceString("DownloadCommand.Arguments.Output"))

    private val _bufferedReaderBufferSize = 1048576

    override fun run() {
        /* Strategy:
        Get input;
        What is it? Possible cases:
            Manifest itself,
            Extracted folder,
            Archive,
            URL
        Retrieve manifest.json data;
        Download all files in a new folder, same parent as boonforge;
        Done.
         */
        echo(getResourceString("DownloadCommand.Gathering"))

        val outputDirectory = Path(output)
        outputDirectory.createDirectories()

        // On the filesystem.
        val inputPath = Path(input).toAbsolutePath()
        if (inputPath.exists()) {
            when (inputPath.extension) {
                "zip" -> {
                    val zipFile = ZipFile(inputPath.toFile())
                    if (!zipFile.isValidZipFile) {
                        echo(getResourceString("DownloadCommand.InvalidZipFile".replace(
                            "^input", input
                        )), err = true)
                        exitProcess(1) // TODO Union status codes together
                    }

                    // So we can put manifest.json in it.
                    val tempDirectory: Path = createTempDirectory("boonforge")

                    zipFile.use {
                        it.extractFile("manifest.json", tempDirectory.pathString)
                    }

                    val manifest = Path("${tempDirectory.pathString}/manifest.json")

                    val manifestBR = manifest.bufferedReader(
                        bufferSize = _bufferedReaderBufferSize
                    )

                    val manifestDecoded = manifestBR.use { _manifestBR ->
                        Json.decodeFromString<Manifest>(_manifestBR.readText())
                    }

                    manifestBR.close()
                    manifest.deleteIfExists()
                    tempDirectory.deleteIfExists()

                    downloadFiles(manifestDecoded, output)

                    zipFile.use {
                        it.extractFile("${manifestDecoded.overrides}/", outputDirectory.pathString)
                    }

                    echo(getResourceString("DownloadCommand.ExtractedOverrides"))

                    zipFile.close()

                    // FIXME Replace not working?
                    echo(getResourceString("DownloadCommand.Finished".replace("^input", input)))

                    exitProcess(0)
                }
            }
        } // else if () {} // On the internet.
    }
}
