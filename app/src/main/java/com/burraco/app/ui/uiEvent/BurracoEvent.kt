package com.burraco.app.ui.uiEvent

import com.burraco.app.data.entity.Player
import com.burraco.app.ui.uiState.SortType

sealed interface BurracoEvent {
    object OnResume : BurracoEvent
    data class OnPlayerSelected(val player: Player) : BurracoEvent
    object ShowAddPlayer : BurracoEvent
    data class AddPLayer(val player: Player) : BurracoEvent
    object HideAddPlayer : BurracoEvent
    object ShowRemovePlayer : BurracoEvent
    data class RemovePlayer(val player: Player) : BurracoEvent
    object HideRemovePlayer : BurracoEvent
    object OnMatchPlayersConfirmed : BurracoEvent
    data class OnTeamSelected(val player: Player): BurracoEvent
    object OnMatchTeamsConfirmed : BurracoEvent
    data class SortPlayer(val sortType: SortType): BurracoEvent
    object HideErrorMessage: BurracoEvent
    //data class onTeamSelected(val player: Player): BurracoEvent
}