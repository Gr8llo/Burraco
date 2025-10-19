package com.burraco.app.navigation


//navigation main graph
sealed class Screen(val route: String) {
    object SetupRoot : Screen("setup_flow")
    object GameRoot : Screen("game_flow")
}

sealed class SetupScreen(val route: String) {
    object Home: SetupScreen("home_screen")
    object Player : SetupScreen("player_screen")
    object Team : SetupScreen("team_screen")
}

sealed class GameScreen(val route: String) {
    object Match : GameScreen("match_screen")
    object ScoreEntry : GameScreen("score_entry_screen/{teamId}")
    object Winner : GameScreen("winner_screen")
    object RulesScreen: GameScreen("rules_screen")
}

//sealed = non può essere istanziata direttamente e le sottoclassi devono essere dichiarate nel file

//oject crea un singleton, un'istanza della classe unica