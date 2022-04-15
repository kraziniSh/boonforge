package com.github.l9cro1xx.boonforge.jsonSerializers

import kotlinx.serialization.Serializable

@Serializable
data class Manifest(
    val minecraft: Minecraft,
    val manifestType: String,
    val manifestVersion: Int,
    val name: String,
    val version: String,
    val author: String,
    val files: List<File>,
    val overrides: String
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

    @Serializable
    data class File(
        val projectID: Int,
        val fileID: Int,
        val required: Boolean
    )
}
