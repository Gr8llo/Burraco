package com.burraco.app.navigation

sealed class Screen(val route: String){
    object Home: Screen("homeScreen")
    object Player: Screen("inputScreen")
    object SelectTeam: Screen("selectTeamScreen")
    object Game: Screen("gameScreen")
    object ScoreEntry: Screen("scoreEntryScreen")
    object Winner: Screen("winnerScreen")
}

//sealed = non può essere istanziata direttamente e le sottoclassi devono essere dichiarate nel file

//oject crea un singleton, un'istanza della classe unica