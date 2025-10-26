package com.burraco.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.burraco.app.navigation.GameScreen
import com.burraco.app.navigation.Screen
import com.burraco.app.navigation.SetupScreen
import com.burraco.app.ui.components.RulesScreen
import com.burraco.app.ui.screen.ClassicalMatchScreen
import com.burraco.app.ui.screen.HistoryScreen
import com.burraco.app.ui.screen.HomeScreen
import com.burraco.app.ui.screen.PlayerSelectionScreen
import com.burraco.app.ui.screen.TeamScreen
import com.burraco.app.ui.screen.WinnerScreen
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.util.adjustedModifier
import com.burraco.app.viewmodel.BurracoViewModel
import com.burraco.app.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

            BurracoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SetupRoot.route
                    ) {
                        navigation(
                            route = Screen.SetupRoot.route,
                            startDestination = SetupScreen.Home.route
                        ) {
                            composable(route = SetupScreen.Home.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screen.SetupRoot.route)
                                }
                                val viewModel: BurracoViewModel = hiltViewModel(parentEntry)
                                val state = viewModel.state.collectAsState()

                                HomeScreen(
                                    modifier = Modifier.adjustedModifier(),
                                    state = state.value,
                                    onEvent = viewModel::onEvent,
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                            composable(route = SetupScreen.Player.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screen.SetupRoot.route)
                                }
                                val viewModel: BurracoViewModel = hiltViewModel(parentEntry)
                                val state = viewModel.state.collectAsState()

                                PlayerSelectionScreen(
                                    modifier = Modifier.adjustedModifier(),
                                    state = state.value,
                                    onEvent = viewModel::onEvent,
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                            composable(route = SetupScreen.Team.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screen.SetupRoot.route)
                                }
                                val viewModel: BurracoViewModel = hiltViewModel(parentEntry)
                                val state = viewModel.state.collectAsState()

                                TeamScreen(
                                    modifier = Modifier.adjustedModifier(),
                                    state = state.value,
                                    onEvent = viewModel::onEvent,
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                            composable(route = SetupScreen.History.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screen.SetupRoot.route)
                                }
                                val viewModel: BurracoViewModel = hiltViewModel(parentEntry)
                                val state = viewModel.state.collectAsState()

                                HistoryScreen(
                                    modifier = Modifier.adjustedModifier(),
                                    state = state.value,
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                        }
                        navigation(
                            route = Screen.GameRoot.route,
                            startDestination = GameScreen.Match.route
                        ) {
                            composable(route = GameScreen.Match.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screen.GameRoot.route)
                                }
                                val viewModel: GameViewModel = hiltViewModel(parentEntry)
                                val state = viewModel.state.collectAsState()

                                ClassicalMatchScreen(
                                    modifier = Modifier.adjustedModifier(),
                                    state = state.value,
                                    onEvent = viewModel::onEvent,
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                            composable(route = GameScreen.Winner.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screen.GameRoot.route)
                                }
                                val viewModel: GameViewModel = hiltViewModel(parentEntry)
                                val state = viewModel.state.collectAsState()

                                WinnerScreen(
                                    modifier = Modifier.adjustedModifier(),
                                    state = state.value,
                                    onEvent = viewModel::onEvent,
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                            composable(route = GameScreen.Rules.route) {
                                RulesScreen(
                                    modifier = Modifier.adjustedModifier(),
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}