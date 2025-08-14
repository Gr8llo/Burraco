package com.burraco.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.burraco.app.ui.screen.HomeScreen
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.viewmodel.BurracoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants


import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.AnimationPlayer
import com.burraco.app.ui.screen.GameScreen
import com.burraco.app.ui.screen.PlayerScreen
import com.burraco.app.ui.screen.ScoreEntryScreen
import com.burraco.app.ui.screen.TeamScreen
import com.burraco.app.ui.screen.WinnerScreen

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            val navController = rememberNavController()
            val burracoViewModel: BurracoViewModel = viewModel()

            BurracoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                modifier = Modifier.adaptiveNavigationBarPadding(),
                                onNavigate = { screen -> navController.navigate(screen) }
                            )
                        }
                        composable(Screen.Player.route) {
                            PlayerScreen(
                                modifier = Modifier.adaptiveNavigationBarPadding(),
                                burracoViewModel = burracoViewModel,
                                onNavigate = { screen -> navController.navigate(screen) }
                            )
                        }
                        composable(Screen.SelectTeam.route) {
                            TeamScreen(
                                modifier = Modifier.adaptiveNavigationBarPadding(),
                                burracoViewModel = burracoViewModel,
                                onNavigate = { screen -> navController.navigate(screen) }
                            )
                        }
                        composable(Screen.Game.route) {
                            GameScreen(
                                modifier = Modifier.adaptiveNavigationBarPadding(),
                                burracoViewModel = burracoViewModel,
                                onNavigate = { screen -> navController.navigate(screen) }
                            )
                        }
                        composable(Screen.ScoreEntry.route + "/{teamSelected}") { navString ->
                            val teamName = navString.arguments?.getString("teamSelected")
                            ScoreEntryScreen(
                                modifier = Modifier.adaptiveNavigationBarPadding(),
                                burracoViewModel = burracoViewModel,
                                onNavigate = { screen -> navController.navigate(screen) },
                                currentTeam = teamName
                            )
                        }
                        composable(Screen.Winner.route){
                            WinnerScreen(
                                modifier = Modifier.adaptiveNavigationBarPadding(),
                                burracoViewModel = burracoViewModel,
                                onNavigate = { screen -> navController.navigate(screen)}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Modifier.adaptiveNavigationBarPadding(thresholdDp: Int = 25): Modifier {
    val bottomPaddingDp = WindowInsets.navigationBars
        .asPaddingValues()
        .calculateBottomPadding() // <-- già Dp, non serve conversione

    val sides = if (bottomPaddingDp > thresholdDp.dp) {
        // Con navigation bar "alta"
        WindowInsetsSides.Top + WindowInsetsSides.Start + WindowInsetsSides.End + WindowInsetsSides.Bottom
    } else {
        // Con gesture bar "bassa"
        WindowInsetsSides.Top + WindowInsetsSides.Start + WindowInsetsSides.End
    }

    return this.windowInsetsPadding(
        WindowInsets.systemBars.only(sides)
    )
}

