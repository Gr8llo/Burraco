package com.burraco.app.ui.uiEvent

import com.burraco.app.model.Team

sealed interface GameEvent {
    object EditPreviousRound : GameEvent
    object InsertNextRound: GameEvent
    data class ChangeTemporaryPoints(val team: Team, val points: Pair<Int, Int>, val pointsError: Boolean): GameEvent
    object ConfirmPoints: GameEvent
    data class SelectFirstTeamToDealCard(val team: Team): GameEvent
    object OnWin: GameEvent
}