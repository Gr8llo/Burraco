package com.burraco.app.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burraco.app.data.GameSessionRepository
import com.burraco.app.data.dao.PlayerDao
import com.burraco.app.model.Team
import com.burraco.app.ui.uiEvent.GameEvent
import com.burraco.app.ui.uiState.GameState
import com.burraco.app.ui.uiState.GameUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val dao: PlayerDao,
    private val repository: GameSessionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())

    val state: StateFlow<GameState> = combine(repository.currentTeams, repository.currentRound, _uiState) { teams, currentRound, uiState ->
        GameState(
            currentRound = currentRound,
            firstRoundTo1000 = if ( teams.any { team -> team.totalScore >= 1000 } && teams.size == 3 ) currentRound else null,
            firstTeamToDealCard = uiState.firstTeamToDealCard,
            showConfirmGame = teams.any { team -> team.totalScore >= 2005 },
            showInputField = uiState.showInputField,
            teams = teams
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GameState() // Stato iniziale vuoto
    )

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.EditPreviousRound -> _uiState.update { it.copy(showInputField = true) }
            is GameEvent.HideInputField -> _uiState.update { it.copy(showInputField = false) }

            is GameEvent.InsertNextRound -> _uiState.update {
                repository.nextRound()
                it.copy(
                    showInputField = true
                )
            }

            is GameEvent.SelectFirstTeamToDealCard -> {
                _uiState.update {
                    it.copy(
                        firstTeamToDealCard = event.team
                    )
                }
            }

            is GameEvent.OnWin -> {
                val teams = repository.currentTeams.value
                val winner: Team = teams.maxBy { it.totalScore }
                teams.forEach { team ->
                    team.players.forEach { player ->
                        viewModelScope.launch {
                            dao.updatePlayer(
                                player.copy(
                                    matchedWon = player.matchedWon + (if (team == winner) 1 else 0),
                                    matchedPlayed = player.matchedPlayed + 1
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}