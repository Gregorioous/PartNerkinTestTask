package org.nerkin.project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CachedItem(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String
)
