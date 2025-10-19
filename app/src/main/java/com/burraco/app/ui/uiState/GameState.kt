package com.burraco.app.ui.uiState

import com.burraco.app.model.Team

data class GameUiState(
    val firstRoundTo1000: Int? = null,   //attivo solo in tre giocatori
    val firstTeamToDealCard: Team? = null,
    val showConfirmGame: Boolean = false,
    val showInputField: Boolean = false,
)

data class GameState(
    val teams: List<Team> = emptyList<Team>(),
    val currentRound: Int = 0,
    val firstRoundTo1000: Int? = null,   //attivo solo in tre giocatori
    val firstTeamToDealCard: Team? = null,
    val showConfirmGame: Boolean = false,
    val showInputField: Boolean = false,
)
