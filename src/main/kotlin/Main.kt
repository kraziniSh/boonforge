/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain
 one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge

import com.github.ajalt.clikt.core.subcommands
import com.github.l9cro1xx.boonforge.commands.BoonForge
import com.github.l9cro1xx.boonforge.commands.CreditsCommand
import com.github.l9cro1xx.boonforge.commands.DownloadCommand
import com.github.l9cro1xx.boonforge.commands.VersionCommand

fun main(args: Array<String>): Unit = BoonForge()
    .subcommands(CreditsCommand(),  DownloadCommand(), VersionCommand())
    .main(args)
