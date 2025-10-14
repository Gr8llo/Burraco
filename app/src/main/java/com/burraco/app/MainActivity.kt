package com.burraco.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.burraco.app.ui.screen.HomeScreen
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.viewmodel.BurracoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import androidx.room.Room
import com.burraco.app.navigation.GameGroupScreen
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.screen.GameScreen
import com.burraco.app.ui.screen.PlayerScreen
import com.burraco.app.ui.screen.ScoreEntryScreen
import com.burraco.app.ui.screen.TeamScreen
import com.burraco.app.ui.screen.WinnerScreen
import com.burraco.app.ui.util.adaptiveNavigationBarPadding
import com.burraco.app.viewmodel.GameScreenViewModel
import com.burraco.app.viewmodel.factory.GameScreenViewModelFactory
import kotlin.getValue

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
                                onNavigate = { screen -> navController.navigate(screen) {} }
                            )
                        }
                        navigation(
                            startDestination = GameGroupScreen.Game.route,
                            route = Screen.Game.route,
                        ) {
                            composable("GameGroupRoot") { /* empty composable */ }
                            composable(GameGroupScreen.Game.route) {
                                val gameScreenViewModel = it.rememberSharedGameViewModel<GameScreenViewModel>(navController, burracoViewModel)
                                GameScreen(
                                    modifier = Modifier.adaptiveNavigationBarPadding(),
                                    gameScreenViewModel = gameScreenViewModel,
                                    onNavigate = { screen -> navController.navigate(screen) }
                                )
                            }
                            composable(GameGroupScreen.ScoreEntry.route + "/{teamSelected}") { it ->
                                val teamName = it.arguments?.getString("teamSelected")
                                val gameScreenViewModel = it.rememberSharedGameViewModel<GameScreenViewModel>(navController, burracoViewModel)
                                ScoreEntryScreen(
                                    modifier = Modifier.adaptiveNavigationBarPadding(),
                                    burracoViewModel = burracoViewModel,
                                    gameScreenViewModel = gameScreenViewModel,
                                    onNavigate = { screen -> navController.navigate(screen) },
                                    currentTeam = teamName
                                )
                            }
                            composable(GameGroupScreen.Winner.route) {
                                val gameScreenViewModel = it.rememberSharedGameViewModel<GameScreenViewModel>(navController, burracoViewModel)

                                WinnerScreen(
                                    modifier = Modifier.adaptiveNavigationBarPadding(),
                                    burracoViewModel = burracoViewModel,
                                    gameScreenViewModel = gameScreenViewModel,
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

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.rememberSharedGameViewModel(
    navController: NavHostController,
    burracoViewModel: BurracoViewModel,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    val factory = remember(parentEntry) {
        GameScreenViewModelFactory(burracoViewModel)
    }

    return viewModel(
        viewModelStoreOwner = parentEntry,   //passando lo scope in automatico jetpackCompose capisce se bisogna o no ricreare il viewModel
        factory = factory
    )
}


