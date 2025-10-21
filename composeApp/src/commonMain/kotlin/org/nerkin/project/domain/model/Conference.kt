package org.nerkin.project.domain.model

data class Conference(
    val id: Int,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val startDate: String?,
    val endDate: String?,
    val place: String?,
    val tags: List<String>,
    val online: Boolean,
    val status: String?
)