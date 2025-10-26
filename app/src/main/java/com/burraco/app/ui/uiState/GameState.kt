package com.burraco.app.ui.uiState

import com.burraco.app.model.Team

data class GameUiState(
    val firstRoundTo1000: Int? = null,   //attivo solo in tre giocatori
    val firstTeamToDealCard: Team? = null,
    val showConfirmGame: Boolean = false,
    val showInputField: Boolean = false,
    val temporaryPoints: Map<String, Pair<Int, Int>> =mapOf(),
    val editPreviousRound: Boolean = false,
    val pointsError: Boolean = false,
    val winner: Team? = null
)

data class GameState(
    val teams: List<Team> = emptyList(),
    val currentRound: Int = 0,
    val firstRoundTo1000Index: Int? = null,
    val firstTeamToDealCard: Team? = null,
    val showConfirmGame: Boolean = false,
    val winner: Team? = null,
    val showInputField: Boolean = false,
    val temporaryPoints: Map<String, Pair<Int, Int>> = mapOf(),
    val eiditPreviousRound: Boolean = false,
    val pointsError: Boolean = false
)
