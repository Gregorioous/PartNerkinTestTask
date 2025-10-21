package org.nerkin.project.data.mappers

import org.nerkin.project.data.api.models.ConferenceDto
import org.nerkin.project.domain.model.Conference

fun ConferenceDto.toDomain(): Conference = Conference(
    id = id,
    title = name,
    description = about,
    imageUrl = image?.url,
    startDate = startDate,
    endDate = endDate,
    place = if (country != null && city != null) "$country, $city" else city ?: country,
    tags = categories.map { it.name },
    online = format == "online",
    status = statusTitle ?: status
)