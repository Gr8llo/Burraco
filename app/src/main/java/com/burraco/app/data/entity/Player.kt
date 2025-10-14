package com.burraco.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    var matchedPlayed: Int = 0,
    var matchedWon: Int = 0,
    var lastUsed: Long = System.currentTimeMillis()
)