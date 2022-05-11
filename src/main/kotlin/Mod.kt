/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge

import com.github.l9cro1xx.boonforge.serializables.ModManifest
import com.github.l9cro1xx.boonforge.serializables.ModpackManifest
import kotlinx.serialization.Serializable

/**
 * Represents a mod, with only essential information needed to resolve it.
 * We primarily use this class in [Modpack] methods.
 *
 * For a more detailed version of a mod, see [ModManifest].
 *
 * Also used in the [ModpackManifest] implementation.
 *
 * @param[projectId] The mod's project ID.
 * @param[fileId] The mod's file ID.
 * @param[required] Whether the mod must be installed for the modpack to work.
 * Optional; defaults to `true` for the safest bet.
 * @constructor
 * @author L9CRO1XX
 * @since 0.1.0
 * @see[ModManifest]
 * @see[ModpackManifest]
 */
@Serializable
data class Mod(
    val projectId: Int,
    val fileId: Int,
    val required: Boolean = true,
)
