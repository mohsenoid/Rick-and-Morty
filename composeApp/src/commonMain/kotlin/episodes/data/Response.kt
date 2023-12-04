package episodes.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val results: List<Result>
)