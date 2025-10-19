package com.burraco.app.ui.uiState

import com.burraco.app.data.entity.Player
import com.burraco.app.model.Team

data class BurracoState(
    val teams: List<Team> = emptyList(),
    val selectedPlayers: List<Player> = emptyList(),
    val allPlayers: List<Player> = emptyList(),
    val showAddPlayer: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null,
    val showRemovePlayer: Boolean = false,
    val showResume: Boolean = false
)

enum class SortType{
    MATCH_WON,
    LAST_PLAY,
}
