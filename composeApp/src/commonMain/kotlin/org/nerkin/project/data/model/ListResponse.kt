package org.nerkin.project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ListResponse(
    val items: List<CachedItem>
)
