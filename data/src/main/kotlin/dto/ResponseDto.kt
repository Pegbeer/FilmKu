package dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    @SerializedName("page")
    val page:Int,
    @SerializedName("results")
    val results:List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages:Int,
    @SerializedName("total_results")
    val totalResults:Int
)