package com.burraco.app.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burraco.app.model.Team
import com.burraco.app.navigation.GameGroupScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.all
import kotlin.collections.any
import kotlin.text.isNotBlank

class GameScreenViewModel  (
    private val burracoViewModel: BurracoViewModel,
) : ViewModel()
{
    val teams = burracoViewModel.teams

    val currentRound = burracoViewModel.currentRound

    val readyToWin = MutableStateFlow(false)

    var lastRowPos = MutableStateFlow(Offset.Zero) //non è una varibile importante non serve il doppio con private

    val firstRoundTo1000 = burracoViewModel.firstRoundTo1000

    var firstTeamToDealCards = MutableStateFlow("")

    init {
        //controllo se qualcuno ha superato 1000 punti nelle partite a tre giocatori
        viewModelScope.launch { //permette di lanciare una coroutine nel viewModelScope (una funzione non bloccante che aspetta che si verofifichi una condizione)
            teams.collect { teamList -> // collect è sospensivo: può aspettare nuovi valori che arrivano nel tempo.
                if(burracoViewModel.firstRoundTo1000.value == 0 )
                    if(teamList.size==3 && teamList.any { it.totalScore >= 1000 })
                        burracoViewModel.firstRoundTo1000.value = currentRound.value
            }
        }

        viewModelScope.launch {
            teams.collect { teamsList ->
                if ((teamsList.all { it.round == currentRound.value }) && teamsList.any { it.totalScore >= 2005 }) { //partita finita
                    readyToWin.value = true
                }
                else{
                    readyToWin.value = false
                }
            }
        }
    }

    fun nextRound() = burracoViewModel.nextRound()
}