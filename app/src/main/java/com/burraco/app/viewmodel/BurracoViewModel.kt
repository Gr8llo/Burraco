package com.burraco.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burraco.app.data.GameSessionRepository
import com.burraco.app.data.dao.PlayerDao
import com.burraco.app.model.Team
import com.burraco.app.ui.uiEvent.BurracoEvent
import com.burraco.app.ui.uiState.BurracoState
import com.burraco.app.ui.uiState.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BurracoViewModel @Inject constructor(
    private val dao: PlayerDao,
    private val repository: GameSessionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BurracoState())
    private val _sortType = MutableStateFlow(SortType.LAST_PLAY)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _players = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.LAST_PLAY -> dao.getPlayerByLastTimePlayed()
            SortType.MATCH_WON -> dao.getPlayerByMatchWon()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    //getAllPlayers è un flow quindi quando cambia viene cambiato automaticamente
    //uno StateFlow immutabile, così non può essere modificato dall'esterno.

    val state = combine(_state, _players) { state, players ->
        state.copy(
            allPlayers = players
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BurracoState())

    fun onEvent(event: BurracoEvent) {
        when (event) {
            is BurracoEvent.ShowAddPlayer -> _state.update {
                it.copy(showAddPlayer = true)
            }

            is BurracoEvent.HideAddPlayer -> _state.update {
                it.copy(showAddPlayer = false)
            }

            is BurracoEvent.AddPLayer -> viewModelScope.launch {
                try {
                    dao.insertPlayer(event.player.copy(name = event.player.name.lowercase()))
                    _state.update {
                        it.copy(
                            showErrorDialog = false,
                            errorMessage = null
                        )
                    }//per sicurezza pulisco i messagi di errore
                } catch (exception: Exception) {
                    val isUniqueConstraintError =
                        exception.message?.contains(
                            "UNIQUE constraint failed",
                            ignoreCase = true
                        ) //ignore case serve per non fare distinzione tra maiuscole e minuscole
                            ?: false

                    if (isUniqueConstraintError) {
                        _state.update {
                            it.copy(
                                errorMessage = "Nome giocatore già esistente",
                                showErrorDialog = true,
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                errorMessage = "Errore sconosciuto",
                                showErrorDialog = true,
                            )
                        }
                    }
                }
            }

            is BurracoEvent.ShowRemovePlayer -> _state.update {
                it.copy(showRemovePlayer = true)
            }

            is BurracoEvent.HideRemovePlayer -> _state.update {
                it.copy(showRemovePlayer = false)
            }

            is BurracoEvent.RemovePlayer -> viewModelScope.launch {
                dao.deletePlayer(event.player)
            }

            is BurracoEvent.OnPlayerSelected -> {
                if (_state.value.selectedPlayers.contains(event.player)) {
                    _state.update {
                        it.copy(
                            selectedPlayers = it.selectedPlayers - event.player
                        )
                    }
                } else if (_state.value.selectedPlayers.size < 4) {
                    _state.update {
                        it.copy(
                            selectedPlayers = it.selectedPlayers + event.player
                        )
                    }
                }
            }

            is BurracoEvent.OnMatchPlayersConfirmed -> {
                val players = _state.value.selectedPlayers

                if (repository.currentTeams.value.isNotEmpty()) repository.clearTeam()

                players.forEach { player -> repository.addPlayer(player) }
                if (players.size < 4) {
                    players.forEach { player ->
                        repository.addTeam(
                            Team(
                                name = player.name,
                                players = listOf(player),
                            )
                        )
                    }
                } else {
                    viewModelScope.launch {
                        _state.update {
                            it.copy(
                                playersWithoutTeam = _state.value.selectedPlayers,
                                playersWithTeam = emptyList()  //pulisco eventuali residui
                            )
                        }
                    }
                }
            }

            is BurracoEvent.OnTeamSelected -> {
                if (_state.value.playersWithoutTeam.contains(event.player)) {  // NO IN, valori in memoria diversi -> contains verifica i parametri
                    _state.update {
                        it.copy(
                            playersWithoutTeam = _state.value.playersWithoutTeam - event.player,
                            playersWithTeam = _state.value.playersWithTeam + event.player
                        )
                    }
                } else if (_state.value.playersWithTeam.contains(event.player)) {
                    _state.update {
                        it.copy(
                            playersWithoutTeam = _state.value.playersWithoutTeam + event.player,
                            playersWithTeam = _state.value.playersWithTeam - event.player
                        )
                    }
                }
            }

            is BurracoEvent.OnMatchTeamsConfirmed -> {

                if (repository.currentTeams.value.isNotEmpty()) repository.clearTeam()  //per evitare traccie di dati vecchi

                val players = _state.value.playersWithTeam
                repository.addTeam(
                    Team(
                        players = listOf(players[0], players[1]),
                        name = "${ players[0].name } & ${ players[1].name }",
                    )
                )
                repository.addTeam(
                    Team(
                        players = listOf(players[2], players[3]),
                        name = "${ players[2].name } & ${ players[3].name }",
                    )
                )
            }

            is BurracoEvent.OnResume -> TODO()
            is BurracoEvent.SortPlayer -> _sortType.value = event.sortType
            is BurracoEvent.HideErrorMessage -> {
                _state.update {
                    it.copy(
                        showErrorDialog = false,
                        errorMessage = null
                    )
                }
            }
        }
    }
}
