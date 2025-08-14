package com.burraco.app.model

data class Team(
    val name: String,
    val players: List<String>,
    val scores: List<Pair<Int, Int>> =  emptyList(),  //Burraco - Carte - Da Pagare
    val round:Int = 0
)
{
    val totalScore: Int
        get() = scores.sumOf { it.first + it.second}

    fun getScoreRound(round: Int): Pair<Int, Int>? {
        return scores.getOrNull(round - 1)
    }
}