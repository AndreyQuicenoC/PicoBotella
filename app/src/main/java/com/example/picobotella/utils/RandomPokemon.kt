package com.example.picobotella.utils
import com.example.picobotella.webservice.ApiUtils

class RandomPokemon {

    private var cachedImageUrls: List<String>? = null

    suspend fun getRandomPokemonImage(): String? {
        val imageUrls = cachedImageUrls ?: ApiUtils.getApiService()
            .getPokemonList()
            .pokemon
            .mapNotNull { pokemon -> pokemon.img.takeIf(String::isNotBlank) }
            .also { cachedImageUrls = it }

        return imageUrls.randomOrNull()
    }

}