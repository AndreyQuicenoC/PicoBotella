package com.example.picobotella.repository

import android.content.Context
import com.example.picobotella.data.ChallengeDB
import com.example.picobotella.data.ChallengeDao
import com.example.picobotella.model.Challenge
import com.example.picobotella.model.PokemonModelResponse
import com.example.picobotella.webservice.ApiService
import com.example.picobotella.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChallengeRepository(val context: Context) {

    private var challengeDao: ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()
    private var apiService: ApiService = ApiUtils.getApiService()

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

    // ==========================================
    // OPERACIONES DE LA API REMOTA (RETROFIT)
    // ==========================================

    suspend fun getPokemonFromApi(): PokemonModelResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPokemonList()
                response
            } catch (e: Exception) {
                e.printStackTrace()
                // Retornamos null o un objeto vacío en caso de error de red
                null
            }
        }
    }
}