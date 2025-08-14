package com.burraco.app.viewmodel

import androidx.lifecycle.ViewModel
import com.burraco.app.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BurracoViewModel: ViewModel() {
    // Stato interno modificabile solo dal ViewModel (lettura e scrittura)
    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    private val _players = MutableStateFlow<List<String>>(emptyList())

    private val _currentRound = MutableStateFlow<Int>(0)

    // Stato pubblico osservabile dalla UI (solo lettura)
    val teams: StateFlow<List<Team>>
        get() = _teams

    val size: Int
        get() = _teams.value.size

    val currentRound: StateFlow<Int>
        get() = _currentRound

    val players: StateFlow<List<String>>
        get() = _players


    fun addTeam(teamName: String, players: List<String>){
        if(teamName in _teams.value.map { it.name })
            return
        val newTeam = Team(players = players, name = teamName)
        players.forEach { player -> addPlayer(player) }
        _teams.value = _teams.value + newTeam // ✅ crea una nuova lista e la assegna
    }

    fun getTeamByName(name: String): Team? {
        return _teams.value.find { it.name == name }
    }

    fun addPlayer(name: String){
        if(name in _players.value)
            return
        _players.value = _players.value + listOf(name)
    }

    fun addScore(teamName: String, points: Pair<Int, Int>) {
        val roundIndex = _currentRound.value -1

        _teams.value = _teams.value.map { team ->
            if (team.name == teamName) {
                val updatedScores = team.scores.toMutableList()

                // Se la lista è più corta del round corrente, la estendo
                while (updatedScores.size <= roundIndex) {
                    updatedScores.add(Pair(0, 0)) // valore placeholder
                }
                // Inseririmento punteggio al round corrente
                updatedScores[roundIndex] = points

                team.copy(scores = updatedScores, round = _currentRound.value)
            } else {
                team
            }
        }
    }

    fun nextRound(){
        _currentRound.value++
    }

    fun resetTeam(){
        _teams.value = emptyList<Team>()
    }

    fun resetScore(){
        _teams.value = _teams.value.map{ team ->
            team.copy(scores = emptyList())
        }
    }
}