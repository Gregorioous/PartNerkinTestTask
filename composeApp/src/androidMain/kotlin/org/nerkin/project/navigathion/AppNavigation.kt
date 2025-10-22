package org.nerkin.project.navigathion

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.nerkin.project.screen.CalendarScreen
import org.nerkin.project.screen.ConferenceDetailScreen
import org.nerkin.project.viewmodel.CalendarViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    viewModel: CalendarViewModel,
    navController: NavHostController
) { // Изменили тип на NavHostController

    LaunchedEffect(navController) {
        Log.d("Navigationtype", "NavController type: ${navController::class.java.simpleName}")
        Log.d("Navigationtype", "Composed at ${System.currentTimeMillis()}")
    }
    NavHost(
        navController = navController,
        startDestination = "calendar"
    ) {

        composable("calendar") {
            CalendarScreen(
                viewModel = viewModel,
                onConferenceClick = { conf ->
                    viewModel.selectConference(conf)
                    navController.navigate("detail")
                }
            )
        }

        composable("detail") {
            ConferenceDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}