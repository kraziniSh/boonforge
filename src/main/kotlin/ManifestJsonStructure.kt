package com.github.lacroixx13.boonforge

import kotlinx.serialization.Serializable

@Serializable
class Manifest(
    val minecraft: Minecraft = UnknownMinecraft,
    val manifestType: String,
    val manifestVersion: Int,
    val name: String = getResourceString("ManifestJson.Unknown.Name"),
    val version: String = getResourceString("ManifestJson.Unknown.Version"),
    val author: String = getResourceString("ManifestJson.Unknown.Author"),
    val files: List<File>,
    val overrides: String
)

private val UnknownMinecraft = Minecraft()

@Serializable
class Minecraft(
    val version: String = getResourceString("ManifestJson.Unknown.MinecraftVersion"),
    val modLoaders: List<ModLoader> = listOf(UnknownModLoader)
)

private val UnknownModLoader = ModLoader(primary = true)

@Serializable
class ModLoader(
    val id: String = getResourceString("ManifestJson.Unknown.ModLoader"),
    val primary: Boolean
)

@Serializable
class File(
    val projectID: Int = -1, // Determines that a file has no IDs; this is unexpected behavior
    val fileID: Int = -1, // ditto
    val required: Boolean
)
