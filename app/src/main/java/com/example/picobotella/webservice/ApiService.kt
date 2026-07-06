package com.example.picobotella.webservice

import com.example.picobotella.model.PokemonModelResponse
import com.example.picobotella.utils.Constants.END_POINT
import retrofit2.http.GET

interface ApiService {
    // Cambiamos getProducts() por getPokemonList() y el tipo de respuesta a tu modelo de Pokémon
    @GET(END_POINT)
    suspend fun getPokemonList(): PokemonModelResponse
}