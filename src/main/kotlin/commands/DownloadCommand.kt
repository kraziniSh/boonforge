/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.l9cro1xx.boonforge.Modpack
import com.github.l9cro1xx.boonforge.getResourceString
import com.github.l9cro1xx.boonforge.serializables.ModpackManifest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.lingala.zip4j.ZipFile
import java.nio.file.Path
import kotlin.io.path.*

class DownloadCommand : CliktCommand(
    name = getResourceString("DownloadCommand.Name"),
    help = getResourceString("DownloadCommand.Help")
) {
    private val input by argument(help = getResourceString("DownloadCommand.Arguments.Input"))
    private val output by argument(help = getResourceString("DownloadCommand.Arguments.Output"))

    override fun run(): Unit = runBlocking {
        echo(getResourceString("DownloadCommand.Gathering"))

        val outputDirectory = Path(output)
        outputDirectory.createDirectories()

        // TODO Check if input is on the web first.

        // If the mod is on the filesystem:
        val inputPath = Path(input).toAbsolutePath()
        if (inputPath.exists()) {
            when (inputPath.extension) {
                "zip" -> {
                    val zipFile = ZipFile(inputPath.toFile())
                    if (!zipFile.isValidZipFile) {
                        echo(
                            getResourceString(
                                "DownloadCommand.InvalidZipFile"
                            ).replace("^input", input)
                        )
                        throw ProgramResult(2) // TODO Document status codes
                    }

                    val modpack: Modpack
                    withContext(Dispatchers.IO) {
                        val tempDirectory: Path = createTempDirectory(prefix = "boonforge")
                        // So we can put manifest.json in it, and maybe use it later.


                        zipFile.use {
                            it.extractFile("manifest.json", tempDirectory.pathString)
                        }

                        val modpackManifest = Path("${tempDirectory.pathString}/manifest.json")

                        val modpackManifestDecoded = modpackManifest.bufferedReader()
                            .use { bufferedReader -> // Quite ugly...
                                return@use Json.decodeFromString<ModpackManifest>(
                                    bufferedReader.readText()
                                )
                            }

                        modpackManifest.deleteIfExists()
                        tempDirectory.deleteIfExists()

                        modpack = Modpack(modpackManifestDecoded)

                        zipFile.use {
                            it.extractFile("${modpack.overrides}/", outputDirectory.pathString)
                        }
                    }

                    echo(getResourceString("DownloadCommand.ExtractedOverrides"))

                    // TODO Uncomment this:
                    //  modpack.downloadModsAsync()
                    echo("Downloading all mods of this modpack is not implemented yet.")

                    zipFile.close()

                    echo(
                        getResourceString("DownloadCommand.Finished")
                            .replace("^input", input)
                    )
                }
                else -> {
                    echo(getResourceString("DownloadCommand.UnsupportedInput"), err = true)
                    throw ProgramResult(3)
                }
            }
        } else {
            echo(getResourceString("DownloadCommand.InputNotFound"))
        }
    }
}
