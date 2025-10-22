package org.nerkin.project.screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.nerkin.project.R

@Composable
fun RelatedEventItem(isNew: Boolean, title: String, rating: Double?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isNew) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF456AEF), RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "New",
                        fontFamily = FontFamily(
                            androidx.compose.ui.text.font.Font(R.font.inter_medium)
                        ),
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp,
                        lineHeight = 15.4.sp, // 140% от 11sp
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = title,
                fontSize = 14.sp,
                color = if (isNew) Color(0xFF456AEF) else Color.Black
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (rating != null) {
                Text(
                    text = "★${rating}",
                    fontSize = 14.sp,
                    color = Color(0xFFFF9900)
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}