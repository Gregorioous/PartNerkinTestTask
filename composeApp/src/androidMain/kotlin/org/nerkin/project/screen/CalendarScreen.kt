package org.nerkin.project.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.nerkin.project.CalendarViewModel
import org.nerkin.project.R
import org.nerkin.project.domain.model.Conference
import org.nerkin.project.screen.ui.CalendarUiState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = koinViewModel(),
    onConferenceClick: (Conference) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(Unit) { viewModel.loadConferences() }

    when (uiState) {
        is CalendarUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CalendarUiState.Error -> {
            val message = (uiState as CalendarUiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка: $message")
            }
        }

        is CalendarUiState.Success -> {
            val conferences = (uiState as CalendarUiState.Success).conferences
            val groupedByMonth = conferences.groupBy { conference ->
                val date = conference.startDate?.let { LocalDate.parse(it) } ?: LocalDate.now()
                "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.year}"
            }.toSortedMap()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(vertical = 12.dp)
            ) {
                groupedByMonth.forEach { (monthYear, monthConferences) ->
                    item {
                        Text(
                            text = monthYear,
                            fontFamily = FontFamily(
                                androidx.compose.ui.text.font.Font(
                                    R.font.inter_semibold
                                )
                            ),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            lineHeight = 25.2.sp,
                            color = Color(0xFF0E1234),
                            modifier = Modifier
                                .padding(start = 16.dp, bottom = 8.dp)
                                .background(Color(0xFF0E1234))
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                    items(monthConferences) { conf ->
                        ConferenceCard(
                            conference = conf,
                            onClick = {
                                viewModel.selectConference(conf);
                                navController.navigate("detail")
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }

        else -> Unit
    }
}