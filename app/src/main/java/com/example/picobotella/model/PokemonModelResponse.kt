package com.example.picobotella.model

import com.google.gson.annotations.SerializedName

data class PokemonModelResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val results: List<PokemonResult>
)