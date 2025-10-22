package org.nerkin.project.screen

import android.os.Build
import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.nerkin.project.domain.model.Conference
import org.nerkin.project.screen.ui.CalendarUiState
import org.nerkin.project.viewmodel.CalendarViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onConferenceClick: (Conference) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState is CalendarUiState.Loading) {
            Log.d("CalendarScreen", "Loading conferences at ${System.currentTimeMillis()}")
            viewModel.loadConferences()
        }
    }

    when (uiState) {
        is CalendarUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
            Log.d("CalendarScreen", "Showing loading at ${System.currentTimeMillis()}")
        }
        is CalendarUiState.Success -> {
            val conferences = (uiState as CalendarUiState.Success).conferences
            val groupedByMonth = conferences.groupBy { conf ->
                val date = conf.startDate?.let { LocalDate.parse(it) } ?: LocalDate.now()
                "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.year}"
            }.toSortedMap()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(vertical = 12.dp)
            ) {
                groupedByMonth.toList().reversed().toMap()
                    .forEach { (monthYear, monthConferences) ->
                    item {
                        Text(
                            text = monthYear,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                        )
                    }

                    items(monthConferences) { conf ->
                        ConferenceCard(
                            conference = conf,
                            onClick = { onConferenceClick(conf) }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }

        is CalendarUiState.Error -> Text("Ошибка: ${(uiState as CalendarUiState.Error).message}")
        is CalendarUiState.Details -> TODO()
    }
}