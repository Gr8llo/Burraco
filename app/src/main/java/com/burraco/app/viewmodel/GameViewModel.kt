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

    val state: StateFlow<GameState> = combine(
        repository.currentTeams,
        repository.currentRound,
        _uiState
    ) { teams, currentRound, uiState ->
        GameState(
            currentRound = currentRound,
            firstRoundTo1000Index = if (teams.any { team -> team.totalScore >= 1000 } && teams.size == 3) currentRound else null,
            firstTeamToDealCard = uiState.firstTeamToDealCard,
            showConfirmGame = teams.any { team -> team.totalScore >= 2005 },
            showInputField = uiState.showInputField,
            teams = teams,
            temporaryPoints = uiState.temporaryPoints,
            eiditPreviousRound = uiState.editPreviousRound,
            winner = uiState.winner
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GameState() // Stato iniziale vuoto
    )

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.EditPreviousRound -> _uiState.update {
                if (state.value.currentRound == 0) return
                it.copy(
                    showInputField = true,
                    editPreviousRound = true,
                    temporaryPoints = repository.currentTeams.value.associate { team ->
                        val oldScores =
                            team.scores.getOrNull(repository.currentRound.value - 1) ?: Pair(0, 0)
                        team.name to oldScores
                    }
                )
            }

            is GameEvent.InsertNextRound -> {
                repository.nextRound()
                _uiState.update {
                    it.copy(
                        showInputField = true,
                        temporaryPoints = repository.currentTeams.value.associate { team ->
                            team.name to Pair(0, 0)
                        }
                    )
                }
            }

            is GameEvent.ChangeTemporaryPoints -> {
                _uiState.update { state ->
                    state.copy(
                        temporaryPoints = state.temporaryPoints.mapValues { (teamName, points) ->
                            if (teamName == event.team.name)
                                event.points
                            else
                                points
                        }
                    )
                }
            }

            is GameEvent.ConfirmPoints -> {
                viewModelScope.launch {  //serve per evitare che nel caso del primo round _uistate update avvenga prima del upsert alla repository e che quidni la ui tenti di accedere ad un indexOutOfBounds, evita la RACE condition
                    repository.currentTeams.value.forEach { team ->
                        repository.upsertRoundPoints(
                            team = team,
                            points = _uiState.value.temporaryPoints[team.name] ?: Pair(0, 0)
                        )
                    }
                    _uiState.update {
                        it.copy(
                            showInputField = false,
                            editPreviousRound = false,
                            winner = if (repository.currentTeams.value.any { team -> team.totalScore >= 2005 })
                                repository.currentTeams.value.maxBy { team -> team.totalScore }
                            else null
                        )
                    }
                }
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
                _uiState.update {
                    it.copy(
                        winner = winner
                    )
                }
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