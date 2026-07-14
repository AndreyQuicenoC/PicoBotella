package com.example.picobotella.utils
import com.example.picobotella.webservice.ApiService
import com.example.picobotella.webservice.ApiUtils
import kotlin.random.Random

class RandomPokemon {

    suspend fun getRandomPokemonImage(): String? {
        val response = ApiUtils.getApiService().getPokemonList()
        val list = response.pokemon
        if (list.isNotEmpty()) {
            val randomIndex = Random.nextInt(list.size)
            return list[randomIndex].img
        }
        return null
    }

}