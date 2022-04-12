package com.github.lacroixx13.boonforge

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class BoonForge: CliktCommand(
    name = getResourceString("App"),
    help = getResourceString("BoonForge.Help").trimIndent()
) {
    override fun run() {
        echo(getResourceString("BoonForge.Run"), err = true)
        // TODO echo(Current language [...]) to be removed
        echo("Current language: ${getResourceString("Language")}", err = true)
    }
}

fun main(args: Array<String>): Unit = BoonForge()
    .subcommands(DownloadCommand())
    .main(args)
