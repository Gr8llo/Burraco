package com.burraco.app.data

import com.burraco.app.data.entity.Player
import com.burraco.app.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository Singleton per mantenere dati di sessione temporanei,
 * come i team selezionati, durante il passaggio tra diversi ViewModel.
 *
 * @Singleton assicura che Hilt crei UNA SOLA istanza di questa classe
 * per tutta la durata dell'applicazione, rendendola un "serbatoio" affidabile.
 */
@Singleton
class GameSessionRepository @Inject constructor() {

    val _currentPlayers = MutableStateFlow<List<Player>>(emptyList())
    val _currentTeams = MutableStateFlow<List<Team>>(emptyList())
    val _currentRound = MutableStateFlow<Int>(0)

    val currentPlayers = _currentPlayers.asStateFlow()
    val currentTeams = _currentTeams.asStateFlow()
    val currentRound = _currentRound.asStateFlow()

    fun addPlayer(player: Player) {
        if (player !in _currentPlayers.value) {
            _currentPlayers.value += player
        }
    }

    fun clearPlayer() {
        _currentPlayers.value = listOf()
    }

    fun addTeam(team: Team) {
        _currentTeams.value += team
    }

    fun clearTeam() {
        _currentTeams.value = emptyList()
    }

    fun upsertRoundPoints(team: Team, points: Pair<Int, Int>) {
        if (_currentRound.value <= 0) return // posto per un Log in futuro

        _currentTeams.update { it ->
            it.map { currentTeam ->
                if (currentTeam == team) {
                    val updatedScores = currentTeam.scores.toMutableList()
                    if (updatedScores.size < currentRound.value)
                        updatedScores.add(points)
                    else
                        updatedScores[currentRound.value - 1] = points
                    currentTeam.copy(scores = updatedScores)
                } else {
                    currentTeam
                }
            }
        }
    }

    fun nextRound() {
        _currentRound.value++
    }

    fun clearSession() {
        _currentTeams.value = emptyList()
        _currentPlayers.value = emptyList()
        _currentRound.value = 0
    }
}
