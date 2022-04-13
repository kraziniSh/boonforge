package com.github.lacroixx13.boonforge

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.lingala.zip4j.ZipFile
import java.io.BufferedReader
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.system.exitProcess

class DownloadCommand: CliktCommand(
    name = getResourceString("DownloadCommand.Name"),
    help = getResourceString("DownloadCommand.Help")
) {
    private val input by argument(help = getResourceString("DownloadCommand.Arguments.Input"))
    private val output by argument() // TODO Output argument help

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

        // On the filesystem
        val inputPath = Path(input).toAbsolutePath()
        if (inputPath.exists()) {
            when (inputPath.extension) {
                "zip" -> {
                    val zipFile = ZipFile(inputPath.toFile())
                    if (!zipFile.isValidZipFile) {
                        echo("$input is not a valid .zip archive. Maybe it's damaged.", err = true)
                        exitProcess(1) // TODO Union status codes together
                    }

                    // So we can put manifest.json in it, and access it later
                    val tempDirectory: Path = createTempDirectory("boonforge")

                    zipFile.use { _zipFile ->
                        _zipFile.extractFile(
                            "manifest.json",
                            tempDirectory.absolutePathString()
                        )
                    }

                    val manifest = Path("${tempDirectory.pathString}/manifest.json")

                    val manifestBR: BufferedReader = manifest.bufferedReader(
                        bufferSize = _bufferedReaderBufferSize
                    )

                    val decodedManifest = Json.decodeFromString<Manifest>(manifestBR.readText())

                    manifestBR.close()
                    manifest.deleteIfExists()
                    tempDirectory.deleteIfExists()

                    // Files
                    downloadFiles(decodedManifest)

                    // Overrides
                    // Get path, then call a fun from that?
                    val outputDirectory = Path(output)
                    outputDirectory.createDirectories()

                    zipFile.extractFile(
                        "${decodedManifest.overrides}/",
                        outputDirectory.absolutePathString()
                    )

                    echo("Extracted overrides")

                    zipFile.close()

                    echo("Finished downloading modpack $input")

                    exitProcess(0)
                }
            }
        } // else if () {} // On the internet
    }
}
