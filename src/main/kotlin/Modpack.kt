package com.github.lacroixx13.boonforge

import com.github.ajalt.clikt.output.TermUi.echo
import java.net.URI


fun getFileInfo(projectID: Int, fileID: Int) {

}

fun downloadFile(projectID: Int, fileID: Int) {
    echo(
        """
            ${getResourceString("Modpack.DownloadFile.Downloading")}
            ${getResourceString("Modpack.DownloadFile.DownloadingProjectID")}
            $projectID
            ${getResourceString("Modpack.DownloadFile.DownloadingFileID")}
            $fileID
            """.trimIndent(),
        err = true
    )

    val fileUri = URI("https://addons-ecs.forgesvc.net/api/v2/addon/$projectID/file/$fileID/")
    val fileJson = fileUri.toASCIIString()

    echo(getResourceString("Modpack.DownloadFile.Done"), err = true)
}

fun downloadFile(idPair: Pair<Int, Int>) {

}

// TODO Check if Pairs content are in correct order
// TODO Rename to downloadFilesFromJson??
fun downloadFiles(idList: Manifest) { /* Could’ve used a Map instead of a Pair...
        ...But using a Map requires null-checking.

        for (i in 0 .. idList.lastIndex) {
            val projectID = idList[i].first
            val fileID = idList[i].second

            DownloadFile(projectID, fileID)
        }
    */
}
