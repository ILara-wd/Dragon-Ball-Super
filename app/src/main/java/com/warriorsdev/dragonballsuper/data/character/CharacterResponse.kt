package com.warriorsdev.dragonballsuper.data.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("specie")
    var specie: String? = null,
    @SerializedName("role")
    var role: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("universe")
    var universe: String? = null,
    @SerializedName("transform")
    var transform: Any? = null,
    @SerializedName("originplanet")
    var originplanet: String? = null
)


data class CharacterDBS(
    var id: Int? = null,
    var name: String? = null,
    var specie: String? = null,
    var role: String? = null,
    var imageUrl: String? = null,
    var status: String? = null,
    var universe: String? = null,
    var transform: Any? = null,
    var originplanet: String? = null
)

fun CharacterResponse.toDomain() = CharacterDBS(
    id = id,
    name = name,
    specie = specie,
    role = role,
    imageUrl = imageUrl,
    status = status,
    universe = universe,
    transform = transform,
    originplanet = originplanet
)