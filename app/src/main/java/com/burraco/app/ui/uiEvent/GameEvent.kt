package com.burraco.app.ui.uiEvent

import com.burraco.app.model.Team

sealed interface GameEvent {
    object EditPreviousRound : GameEvent
    object InsertNextRound: GameEvent
    object HideInputField : GameEvent
    data class SelectFirstTeamToDealCard(val team: Team): GameEvent
    object OnWin: GameEvent
}