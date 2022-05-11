/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge.serializables

import com.github.l9cro1xx.boonforge.Mod
import kotlinx.serialization.Serializable

/**
 * Contains information about the modpack, such as
 * IDs of its files.
 *
 * @author JsonToKotlinClass (IntelliJ IDEA plugin)
 * @author L9CRO1XX
 * @since 0.1.0
 */
@Serializable
class ModpackManifest(
    val minecraft: Minecraft,
    val manifestType: String = "minecraftModpack", // TODO Check against this?
    // Update: forgot what the TO-DO meant lol
    val manifestVersion: Byte,
    val name: String,
    val version: String,
    val author: String,
    val files: List<Mod>,
    val overrides: String = "overrides",
) {
    @Serializable
    data class Minecraft(
        val version: String,
        val modLoaders: List<ModLoader>
    ) {
        @Serializable
        data class ModLoader(
            val id: String,
            val primary: Boolean
        )
    }
}
