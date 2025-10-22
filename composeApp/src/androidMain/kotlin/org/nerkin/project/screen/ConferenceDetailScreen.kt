package org.nerkin.project.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import org.nerkin.project.CalendarViewModel
import org.nerkin.project.R
import org.nerkin.project.extensions.calculateDuration
import org.nerkin.project.extensions.parseDate
import org.nerkin.project.screen.ui.ConferenceTags
import org.nerkin.project.screen.ui.LocationInfo
import org.nerkin.project.screen.ui.RelatedEvents

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConferenceDetailScreen(
    viewModel: CalendarViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val selectedConference by viewModel.selectedConference.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFFFFF)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Конференция",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 22.4.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = selectedConference?.title ?: "",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_semibold)),
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

            ConferenceTags(tags = selectedConference?.tags ?: emptyList())

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(Color(0xFF060A3C), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "Date",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${selectedConference?.startDate?.let { parseDate(it) } ?: ""}, " +
                            "${
                                calculateDuration(
                                    selectedConference?.startDate,
                                    selectedConference?.endDate
                                )
                            } дней",
                    fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 25.6.sp,
                    color = Color.White
                )
            }

            LocationInfo(
                place = selectedConference?.place ?: "Онлайн",
                iconTint = Color(0xFF456AEF)
            )

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF456AEF))
            ) {
                Text(
                    text = "Регистрация",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Text(
                text = "Связанные события",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_semibold)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 25.2.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 16.dp)
            )
            RelatedEvents()

            Text(
                text = "О событии",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_semibold)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 25.2.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = selectedConference?.description ?: "",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Ключевые возможности, которые предоставляет ${selectedConference?.title ?: ""}:",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_semibold)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 25.2.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "программа, нацеленная на удовлетворение ваших потребностей и интересов;\n" +
                        "лучшие спикеры — знания и опыт экспертов помогут погрузиться в мир Ethereum глубже;\n" +
                        "глобальная аудитория — взаимодействие с участниками мероприятия, включая 20 тысяч посетителей;\n" +
                        "общественные мероприятия — участие в демонстрационном дне и мастер-классах, чтобы еще глубже погрузиться в мир Ethereum.",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Кому будет полезна ${selectedConference?.title ?: ""}:",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_bold)),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "разработчики и программисты, интересующиеся блокчейном и Ethereum;\n" +
                        "предприниматели, исследующие возможности в области децентрализованных приложений;\n" +
                        "инвесторы, ищущие новые перспективы в индустрии криптовалют;\n" +
                        "активисты, стремящиеся к развитию сообщества Ethereum.",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Приглашаем вас присоединиться к ${selectedConference?.title ?: ""} и стать частью коллективного стремления реализовать потенциал Ethereum!",
                fontFamily = FontFamily(androidx.compose.ui.text.font.Font(R.font.inter_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = Color(0xFF0E1234),
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}


