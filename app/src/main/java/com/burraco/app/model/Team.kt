package com.burraco.app.model

import com.burraco.app.data.entity.Player

data class Team(
    val name: String,
    val players: List<Player>,
    val scores: List<Pair<Int, Int>> =  emptyList(),  //Burraco - Carte
    val round:Int = 0
)
{
    val totalScore: Int
        get() = scores.sumOf { it.first + it.second}

    fun getScoreRound(round: Int): Pair<Int, Int>? {
        return scores.getOrNull(round - 1)
    }
}