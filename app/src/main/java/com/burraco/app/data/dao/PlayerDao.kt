package com.burraco.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.burraco.app.data.entity.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("UPDATE player SET matchedPlayed = matchedPlayed + 1")
    suspend fun updateMatchPlayed()

    @Query("UPDATE player SET matchedWon = matchedWon + 1")
    suspend fun updateMatchWon()

    @Query("SELECT * FROM player ORDER BY matchedWon ASC")
    suspend fun getPlayerByMatchWon(): Flow<List<Player>>

    @Query("SELECT * FROM player ORDER BY matchedWon ASC")
    suspend fun getAllPlayers(): Flow<List<Player>>
}