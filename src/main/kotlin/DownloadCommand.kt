package com.github.lacroixx13.boonforge

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.util.zip.ZipFile
import kotlin.io.path.*

class DownloadCommand: CliktCommand(
    name = getResourceString("DownloadCommand.Name"),
    help = getResourceString("DownloadCommand.Help")
) {
    private val input by argument(help = getResourceString("DownloadCommand.Arguments.Input"))
    private val output by argument() // TODO Output argument help

    @ExperimentalSerializationApi
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
        echo(getResourceString("DownloadCommand.Gathering"), err = true)

        // On the filesystem
        val inputPath = Path(input).toAbsolutePath()
        if (inputPath.exists()) {
            when (inputPath.extension) {
                "zip" -> {
                    val zipFile = ZipFile(inputPath.toFile())

                    val manifestJsonIS = zipFile.getInputStream(
                        zipFile.getEntry("manifest.json")
                    )
                    val manifestJsonDecoded = Json.decodeFromStream<Manifest>(manifestJsonIS)

                    downloadFiles(manifestJsonDecoded)

                    val outputFolder = Path(output)
                    outputFolder.createDirectories()

                    val overrides = zipFile.getInputStream(
                        zipFile.getEntry(manifestJsonDecoded.overrides)
                    )

                    val outputFolderStream = outputFolder.outputStream()
                    outputFolderStream.buffered()

                    zipFile.close()
                }
            }
        } // else if () {} // On the internet
    }
}
