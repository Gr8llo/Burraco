package com.burraco.app.ui.uiState

import com.burraco.app.model.Team

data class BurracoState(
    val teams: List<Team> = emptyList<Team>(),
    val players: List<String> = emptyList<String>(),
    val currentRound: Int = 0
)
