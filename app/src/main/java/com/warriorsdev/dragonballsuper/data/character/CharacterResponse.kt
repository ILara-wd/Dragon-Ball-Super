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