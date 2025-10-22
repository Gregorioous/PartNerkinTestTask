package org.nerkin.project.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nerkin.project.domain.model.Conference

@Serializable
data class ImageDto(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String? = null,
    @SerialName("preview") val preview: String? = null,
    @SerialName("placeholder_color") val placeholderColor: String? = null,
    @SerialName("width") val width: Int? = null,
    @SerialName("height") val height: Int? = null
)

@Serializable
data class CategoryDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class TypeDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class ConferenceDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("format") val format: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("status_title") val statusTitle: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("image") val image: ImageDto? = null,
    @SerialName("rating") val rating: Int? = null,
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null,
    @SerialName("oneday") val oneday: Int? = null,
    @SerialName("custom_date") val customDate: String? = null,
    @SerialName("country_id") val countryId: Int? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("city_id") val cityId: Int? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("categories") val categories: List<CategoryDto> = emptyList(),
    @SerialName("type_id") val typeId: Int? = null,
    @SerialName("type") val type: TypeDto? = null,
    @SerialName("about") val about: String? = null,
    @SerialName("register_url") val registerUrl: String? = null
)

@Serializable
data class ListResponse(
    @SerialName("error") val error: ApiError? = null,
    @SerialName("data") val data: DataBlock? = null
)

@Serializable
data class DataBlock(
    @SerialName("counts") val counts: Int? = null,
    @SerialName("pagination") val pagination: Pagination? = null,
    @SerialName("result") val result: List<ConferenceWrapper> = emptyList()
)

@Serializable
data class Pagination(
    @SerialName("page_size") val pageSize: Int? = null,
    @SerialName("current_page") val currentPage: Int? = null
)

@Serializable
data class ConferenceWrapper(
    @SerialName("view_type") val viewType: Int,
    @SerialName("conference") val conference: ConferenceDto
)

@Serializable
data class ViewResponse(
    @SerialName("error") val error: ApiError? = null,
    @SerialName("data") val result: ConferenceDto? = null
)

@Serializable
data class ApiError(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String
)


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