package com.burraco.app.ui.uiEvent

sealed interface BurracoEvent {
    data class addTeam(val teamName: String, val players: List<String>) : BurracoEvent
    data class addPlayer(val name: String): BurracoEvent
    data class addScore(val teamName: String, val points: Pair<Int,Int>): BurracoEvent
    object nextRound: BurracoEvent
    object resetTeam: BurracoEvent
    object resetScore: BurracoEvent
}