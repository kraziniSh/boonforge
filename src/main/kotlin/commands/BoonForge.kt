/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain
 one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.l9cro1xx.boonforge.getResourceString

class BoonForge: CliktCommand(
    name = getResourceString("App"),
    help = getResourceString("BoonForge.Help").trimIndent()
) {
    override fun run() {
        echo(getResourceString("BoonForge.Run"))
        // TODO echo(Current language [...]) to be removed
        echo("Current language: ${getResourceString("Language")}", err = true)
    }
}
