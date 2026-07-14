package com.example.picobotella.model

import com.google.gson.annotations.SerializedName
import com.example.picobotella.model.PokemonResult

data class PokemonModelResponse(
    @SerializedName("pokemon")
    val pokemon: List<PokemonResult>
)
