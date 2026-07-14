package com.example.picobotella.model

import com.google.gson.annotations.SerializedName

data class PokemonModelResponse(
    @SerializedName("pokemon")
    val pokemon: List<PokemonResult>
)
