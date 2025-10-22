package org.nerkin.project.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.nerkin.project.domain.model.Conference
import org.nerkin.project.extensions.parseDayMonth
import org.nerkin.project.screen.ui.ConferenceTags

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConferenceCard(conference: Conference, onClick: () -> Unit) {
    val (startDay, startMonth) = conference.startDate?.parseDayMonth() ?: ("" to "")
    val (endDay, endMonth) = conference.endDate?.parseDayMonth() ?: ("" to "")

    val isCanceled = conference.status?.contains("Отмен", ignoreCase = true) == true

    val backgroundColor = if (isCanceled)
        Color(0xFFFF3333).copy(alpha = 0.1f)
    else
        Color(0xFFEFF2F9)

    val statusColor = if (isCanceled) Color(0xFFFF3333) else Color.Transparent

    val statusText = conference.status ?: conference.status ?: ""


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if (conference.status?.isNotEmpty() == true) 12.dp else 6.dp)
        ) {

            if (isCanceled) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .background(Color.Transparent, RoundedCornerShape(50))
                        .border(
                            width = 1.dp,
                            color = statusColor,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = statusText,
                        color = statusColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = conference.title,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 30.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.04f), RoundedCornerShape(12.dp))
            ) {
                Box(
                    modifier = Modifier
                        .width(156.dp)
                        .height(104.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                ) {
                    AsyncImage(
                        model = conference.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }

                Spacer(modifier = Modifier.width(36.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 42.dp, top = 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = startDay.toIntOrNull()
                                ?.let { if (it < 10) "0$it" else it.toString() } ?: startDay,
                            color = Color(0xFF0E1234),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Light,
                            lineHeight = (40 * 1.4).sp,
                            letterSpacing = (-0.02).sp
                        )
                        Text(
                            text = "–",
                            color = Color(0xFF0E1234),
                            fontSize = 40.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        Text(
                            text = endDay.toIntOrNull()
                                ?.let { if (it < 10) "0$it" else it.toString() } ?: endDay,
                            color = Color(0xFF0E1234),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Light,
                            lineHeight = (40 * 1.4).sp,
                            letterSpacing = (-0.02).sp
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = startMonth.uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF0E1234).copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(55.dp))
                        Text(
                            text = endMonth.uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF0E1234).copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            ConferenceTags(tags = conference.tags)


            Spacer(modifier = Modifier.height(12.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Place,
                    contentDescription = "location",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = conference.place ?: "Онлайн",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}