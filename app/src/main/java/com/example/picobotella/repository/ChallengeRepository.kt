package com.example.picobotella.repository

import android.content.Context
import com.example.picobotella.data.ChallengeDB
import com.example.picobotella.data.ChallengeDao
import com.example.picobotella.model.Challenge
import com.example.picobotella.model.PokemonModelResponse
import com.example.picobotella.utils.RandomPokemon
import com.example.picobotella.webservice.ApiService
import com.example.picobotella.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChallengeRepository(val context: Context) {

    private var challengeDao: ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()
    private var pokemon: RandomPokemon = RandomPokemon()

    // ==========================================
    // OPERACIONES DE LA BASE DE DATOS LOCAL (ROOM)
    // ==========================================

    suspend fun saveChallenge(challenge: Challenge) {
        withContext(Dispatchers.IO) {
            challengeDao.saveChallenge(challenge)
        }
    }

    suspend fun getListChallenge(): MutableList<Challenge> {
        return withContext(Dispatchers.IO) {
            challengeDao.getListChallenge()
        }
    }

    suspend fun deleteChallenge(challenge: Challenge) {
        withContext(Dispatchers.IO) {
            challengeDao.deleteChallenge(challenge)
        }
    }

    // ==========================================
    // OPERACIONES DE LA API REMOTA (RETROFIT)
    // ==========================================

    suspend fun getPokemonFromApi(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = pokemon.getRandomPokemonImage()
                response
            } catch (e: Exception) {
                e.printStackTrace()
                // Retornamos null o un objeto vacío en caso de error de red
                null
            }
        }
    }
}