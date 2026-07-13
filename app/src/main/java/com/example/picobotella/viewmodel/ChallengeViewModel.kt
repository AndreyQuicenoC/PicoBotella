package com.example.picobotella.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.picobotella.model.Challenge
import com.example.picobotella.model.PokemonResult
import com.example.picobotella.repository.ChallengeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ChallengeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChallengeRepository = ChallengeRepository(application)

    // --- Estructura de Observadores al estilo del Docente ---
    private val _listChallenge = MutableLiveData<MutableList<Challenge>>()
    val listChallenge: LiveData<MutableList<Challenge>> get() = _listChallenge

    private val _progresState = MutableLiveData<Boolean>()
    val progresState: LiveData<Boolean> get() = _progresState

    // --- Livedatas del Estado del Juego (Home / Toolbar) ---
    private val _isAudioOn = MutableLiveData<Boolean>(true)
    val isAudioOn: LiveData<Boolean> get() = _isAudioOn

    private val _countdown = MutableLiveData<Int?>()
    val countdown: LiveData<Int?> get() = _countdown

    private val _bottleRotation = MutableLiveData<Float>(0f)
    val bottleRotation: LiveData<Float> get() = _bottleRotation

    private val _isSpinning = MutableLiveData<Boolean>(false)
    val isSpinning: LiveData<Boolean> get() = _isSpinning

    // Resultado final combinando el reto local y el Pokémon aleatorio de la API
    private val _randomChallengeResult = MutableLiveData<Pair<Challenge, PokemonResult>?>()
    val randomChallengeResult: LiveData<Pair<Challenge, PokemonResult>?> get() = _randomChallengeResult

    private var currentDegrees = 0f

    // --- LÓGICA DE AUDIO (HU 3.0) ---
    fun toggleAudio() {
        _isAudioOn.value = !(_isAudioOn.value ?: true)
    }

    // --- LÓGICA CRUD DE RETOS ALINEADA AL DOCENTE ---
    fun getListChallenge() {
        _progresState.value = true
        viewModelScope.launch {
            try {
                _listChallenge.value = repository.getListChallenge()
            } finally {
                _progresState.value = false
            }
        }
    }

    fun insertChallenge(description: String) = viewModelScope.launch {
        _progresState.value = true
        repository.saveChallenge(Challenge(description = description))
        getListChallenge() // Recarga la lista inmediatamente al estilo del profesor
    }

    fun updateChallenge(challenge: Challenge) = viewModelScope.launch {
        _progresState.value = true
        repository.updateChallenge(challenge)
        getListChallenge()
    }

    fun deleteChallenge(challenge: Challenge) = viewModelScope.launch {
        _progresState.value = true
        repository.deleteChallenge(challenge)
        getListChallenge()
    }

    // --- LÓGICA DE GIRO (HU 11) ---
    fun spinBottle() {
        if (_isSpinning.value == true) return

        viewModelScope.launch {
            try {
                _isSpinning.value = true
                _countdown.value = null

                val minTurns = 4
                val maxTurns = 8
                val randomDegrees = Random.nextInt(minTurns * 360, maxTurns * 360).toFloat()

                // Siempre gira en el mismo sentido (horario), acumulando grados
                currentDegrees += randomDegrees
                _bottleRotation.value = currentDegrees

                delay(4000)

                for (i in 3 downTo 0) {
                    _countdown.value = i
                    delay(1000)
                }

                delay(400)
            } finally {
                finishSpinRound()
            }
        }
    }

    private fun finishSpinRound() {
        _countdown.value = null
        _isSpinning.value = false
    }

    // Reservado para HU 12: mostrar reto aleatorio con Pokémon
    private suspend fun fetchRandomChallengeAndPokemon() {
        try {
            // Cargar retos de la BD de forma asíncrona si la lista está vacía en memoria
            val localChallenges = _listChallenge.value ?: repository.getListChallenge()
            val randomChallenge = if (!localChallenges.isNullOrEmpty()) {
                localChallenges[Random.nextInt(localChallenges.size)]
            } else {
                Challenge(id = 0, description = "¡Reto por defecto! Baila por 1 minuto.")
            }

            // Consumir el listado de Pokémon utilizando el repositorio corregido del profesor
            val pokemonResponse = repository.getPokemonFromApi()
            val pokemonList = pokemonResponse?.results // Accedemos a '.results' del mapping que hicimos

            if (!pokemonList.isNullOrEmpty()) {
                val randomPokemon = pokemonList[Random.nextInt(pokemonList.size)]
                _randomChallengeResult.value = Pair(randomChallenge, randomPokemon)
            } else {
                // Respaldo si no hay internet o falla la API
                _randomChallengeResult.value = Pair(randomChallenge, PokemonResult("Univalle", ""))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun resetGameState() {
        _isSpinning.value = false
        _countdown.value = null
        _randomChallengeResult.value = null
    }
}