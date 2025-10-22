package org.nerkin.project.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import org.nerkin.project.CalendarViewModel
import org.nerkin.project.extensions.calculateDuration
import org.nerkin.project.extensions.parseDate
import org.nerkin.project.screen.ui.LocationInfo
import org.nerkin.project.screen.ui.RelatedEvents

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConferenceDetailScreen(
    viewModel: CalendarViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val selectedConference by viewModel.selectedConference.collectAsState()
    LaunchedEffect(Unit) {
        Log.d("ConferenceDetailScreen", "Initial load, selected: ${selectedConference?.title}")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            if (selectedConference == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Загрузка данных...")
                    Log.d("ConferenceDetailScreen", "Selected conference is null")
                }
            } else {
                Log.d(
                    "ConferenceDetailScreen",
                    "Displaying conference: ${selectedConference?.title}"
                )
                Text(
                    text = "Конференция",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 22.4.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = selectedConference?.title ?: "Нет данных",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    lineHeight = 28.8.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Image(
                    painter = rememberAsyncImagePainter(selectedConference?.imageUrl),
                    contentDescription = selectedConference?.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(219.dp)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(top = 16.dp)

                ) {
                    selectedConference?.tags?.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFEFF2F9), RoundedCornerShape(60.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = tag,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Date",
                        tint = Color(0xFF456AEF),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "${selectedConference?.startDate?.let { parseDate(it) } ?: ""}, " +
                                "${
                                    calculateDuration(
                                        selectedConference?.startDate,
                                        selectedConference?.endDate
                                    )
                                } дня",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color(0xFF060A3C)
                    )
                }
                Spacer(Modifier.height(12.dp))
                LocationInfo(
                    place = selectedConference?.place ?: "Онлайн",
                    iconTint = Color(0xFF456AEF)
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { /* Логика регистрации */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF456AEF))
                ) {
                    Text(
                        text = "Регистрация",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Связанные события",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 25.2.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 16.dp)
                )
                RelatedEvents()

                Text(
                    text = "О событии",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 25.2.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = selectedConference?.description ?: "",
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Ключевые возможности, которые предоставляет ${selectedConference?.title ?: ""}:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 25.2.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "• разработчики и программисты, интересующиеся блокчейном и Ethereum;\n" +
                            "• предприниматели, исследующие возможности в области децентрализованных приложений;\n" +
                            "• инвесторы, ищущие новые перспективы в индустрии криптовалют;\n" +
                            "• активисты, стремящиеся к развитию сообщества Ethereum.",
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Кому будет полезна ${selectedConference?.title ?: ""}:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "• разработчики и программисты, интересующиеся блокчейном и Ethereum;\n" +
                            "• предприниматели, исследующие возможности в области децентрализованных приложений;\n" +
                            "• инвесторы, ищущие новые перспективы в индустрии криптовалют;\n" +
                            "• активисты, стремящиеся к развитию сообщества Ethereum.",
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Приглашаем вас присоединиться к ${selectedConference?.title ?: ""} и стать частью коллективного стремления реализовать потенциал Ethereum!",
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF0E1234),
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            }
        }
    }
}