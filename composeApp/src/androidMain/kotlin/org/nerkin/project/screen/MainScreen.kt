package org.nerkin.project.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.nerkin.project.R
import org.nerkin.project.navigathion.AppNavigation
import org.nerkin.project.screen.ui.CalendarUiState
import org.nerkin.project.viewmodel.CalendarViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: CalendarViewModel = koinViewModel() // Один экземпляр
    val navController = rememberNavController() as NavHostController
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        Log.d("MyApp", "Composed at ${System.currentTimeMillis()}")
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Конференции",
                            fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_medium)),
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            lineHeight = 25.2.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "calendar") {
                                    navController.popBackStack()
                                }
                            },
                            enabled = navController.currentBackStackEntry?.destination?.route != "calendar"
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = if (navController.currentBackStackEntry?.destination?.route != "calendar") Color.Black else Color.Gray
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Обработка поддержки */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_headset),
                                contentDescription = "Support",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFFFFFF)
                    )
                )
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (uiState is CalendarUiState.Loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    AppNavigation(viewModel = viewModel, navController = navController)
                }
            }
        }
    }
}