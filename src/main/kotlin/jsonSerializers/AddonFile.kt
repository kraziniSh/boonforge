package com.github.l9cro1xx.boonforge.jsonSerializers

import kotlinx.serialization.Serializable


@Serializable
data class AddonFile(
    val id: Int,
    val displayName: String,
    val fileName: String,
    val fileDate: String,
    val fileLength: Int,
    val releaseType: Int,
    val fileStatus: Int,
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
    val categorySectionPackageType: Int,
    val restrictProjectFileAccess: Int,
    val projectStatus: Int,
    val renderCacheId: Int,
    val fileLegacyMappingId: Int?, // FIXME Verify exact type
    val projectId: Int,
    val parentProjectFileId: Int?, // FIXME Verify exact type
    val parentFileLegacyMappingId: Int?, // FIXME Verify exact type
    val fileTypeId: Int?, // FIXME Verify exact type
    val exposeAsAlternative: Boolean?, // FIXME Find exact type
    val packageFingerprintId: Int,
    val gameVersionDateReleased: String,
    val gameVersionMappingId: Int,
    val gameVersionId: Int,
    val gameId: Int,
    val isServerPack: Boolean,
    val serverPackFileId: Int?, // FIXME Verify exact type
    // FIXME Find exact type; could be e.g. vanilla, modded, etc...
    val gameVersionFlavor: String?,
    val hashes: List<Hash>,
    val downloadCount: Int
) {
    @Serializable
    data class Dependency(
        val id: Int,
        val addonId: Int,
        val type: Int,
        val fileId: Int
    )

    @Serializable
    data class Module(
        val foldername: String, // sic
        val fingerprint: Long,
        val type: Int
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
        val algorithm: Int,
        val value: String
    )
}
