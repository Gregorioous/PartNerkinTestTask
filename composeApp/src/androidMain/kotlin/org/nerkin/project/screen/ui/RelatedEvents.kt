package org.nerkin.project.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RelatedEvents() {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        RelatedEventItem(
            isNew = true,
            title = "ЛАС-ВЕГАС ЯНВ 24",
            rating = null,
            onClick = {}
        )
        RelatedEventItem(
            isNew = false,
            title = "ЛАС-ВЕГАС ЯНВ 24",
            rating = 5.0,
            onClick = {}
        )
        RelatedEventItem(
            isNew = false,
            title = "ЛАС-ВЕГАС ЯНВ 23",
            rating = 8.3,
            onClick = {}
        )
    }
}

