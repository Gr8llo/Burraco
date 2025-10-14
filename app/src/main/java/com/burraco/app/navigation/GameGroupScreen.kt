package com.burraco.app.navigation

sealed class GameGroupScreen(val route:String){
        object Game: Screen(route = "gameScreen")
        object ScoreEntry: Screen("scoreEntryScreen")
        object Winner: Screen("winnerScreen")
}