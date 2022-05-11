/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge.serializables

import com.github.l9cro1xx.boonforge.Mod
import com.github.l9cro1xx.boonforge.Modpack
import kotlinx.serialization.Serializable

/**
 * Represents detailed information about a mod.
 *
 * For a simpler and more lightweight version of a mod, see [Mod].
 * We primarily use [Mod] in [Modpack] methods.
 *
 * @param[id] The file ID of this mod.
 * @param[projectId] The project ID of this mod.
 * @author JsonToKotlinClass (IntelliJ IDEA plugin)
 * @author L9CRO1XX
 * @since 0.1.0
 * @see[Mod]
 */
@Serializable
data class ModManifest(
    val id: Int,
    val displayName: String,
    val fileName: String,
    val fileDate: String,
    val fileLength: Int,
    val releaseType: Byte,
    val fileStatus: Byte,
    val downloadUrl: String,
    val isAlternate: Boolean,
    val alternateFileId: Int,
    val dependencies: List<Dependency>,
    val isAvailable: Boolean,
    val modules: List<Module>,
    val packageFingerprint: Long,
    val gameVersion: List<String>,
    val sortableGameVersion: List<SortableGameVersion>,
    val installMetadata: String?, // FIXME Find exact type
    val changelog: String?, // FIXME Find exact type
    val hasInstallScript: Boolean,
    val isCompatibleWithClient: Boolean,
    val categorySectionPackageType: Byte,
    val restrictProjectFileAccess: Byte,
    val projectStatus: Byte,
    val renderCacheId: Int,
    val fileLegacyMappingId: Int?, // FIXME Verify exact type
    val projectId: Int,
    val parentProjectFileId: Int?, // FIXME Verify exact type
    val parentFileLegacyMappingId: Int?, // FIXME Verify exact type
    val fileTypeId: Int?, // FIXME Verify exact type
    val exposeAsAlternative: Boolean?, // FIXME Find exact type
    val packageFingerprintId: Long,
    val gameVersionDateReleased: String,
    val gameVersionMappingId: Int,
    val gameVersionId: Int,
    val gameId: Short,
    val isServerPack: Boolean,
    val serverPackFileId: Int?, // FIXME Verify exact type
    // FIXME Find exact type; could be e.g. vanilla, modded, etc...
    val gameVersionFlavor: String?,
    val hashes: List<Hash>,
    val downloadCount: Int,
) {
    @Serializable
    data class Dependency(
        val id: Int,
        val addonId: Int,
        val type: Byte,
        val fileId: Int,
    )

    @Serializable
    data class Module(
        val foldername: String, // sic
        val fingerprint: Long,
        val type: Byte,
    )

    @Serializable
    data class SortableGameVersion(
        val gameVersionPadded: String,
        val gameVersion: String,
        val gameVersionReleaseDate: String,
        val gameVersionName: String,
        val gameVersionTypeId: Int
    )

    @Serializable
    data class Hash(
        val algorithm: Byte,
        val value: String,
    )
}
