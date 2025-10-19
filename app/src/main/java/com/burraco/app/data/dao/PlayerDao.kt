package com.burraco.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.burraco.app.data.entity.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)

    @Query("SELECT * FROM player ORDER BY matchedWon DESC")
    fun getPlayerByMatchWon(): Flow<List<Player>>

    @Query("SELECT * FROM player ORDER BY lastPlayed DESC")
    fun getPlayerByLastTimePlayed(): Flow<List<Player>>

    @Query("SELECT * FROM player")
    fun getAllPlayers(): Flow<List<Player>>
}