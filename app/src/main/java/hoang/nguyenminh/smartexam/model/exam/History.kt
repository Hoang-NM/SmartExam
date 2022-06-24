package hoang.nguyenminh.smartexam.model.exam

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExamHistory(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "executedDate") val executedDate: String
)

@JsonClass(generateAdapter = true)
data class ExamDetail(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "executedDate") val executedDate: String,
    @Json(name = "result") val result: String,
)