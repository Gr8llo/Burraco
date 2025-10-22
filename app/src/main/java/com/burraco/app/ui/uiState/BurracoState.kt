package com.burraco.app.ui.uiState

import com.burraco.app.data.entity.Player

data class BurracoState(
    val selectedPlayers: List<Player> = emptyList(),
    val allPlayers: List<Player> = emptyList(),
    val showAddPlayer: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null,
    val showRemovePlayer: Boolean = false,
    val showResume: Boolean = false,
    val playersWithTeam: List<Player> = listOf(),
    val playersWithoutTeam: List<Player> = listOf(),
)

enum class SortType{
    MATCH_WON,
    LAST_PLAY,
}
