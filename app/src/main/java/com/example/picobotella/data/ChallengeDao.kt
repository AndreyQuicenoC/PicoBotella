package com.example.picobotella.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.picobotella.model.Challenge

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChallenge(challenge: Challenge)

    // HU 6.0 Criterio 6: Obtenemos los retos ordenados por ID descendente
    // para que el reto más nuevo siempre aparezca arriba en la lista.
    @Query("SELECT * FROM Challenge ORDER BY id DESC")
    suspend fun getListChallenge(): MutableList<Challenge>

    @Delete
    suspend fun deleteChallenge(challenge: Challenge)

    @Update
    suspend fun updateChallenge(challenge: Challenge)
}