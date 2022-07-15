package hoang.nguyenminh.smartexam.exception

import androidx.annotation.StringRes
import hoang.nguyenminh.smartexam.model.ErrorResponse

class ErrorResponseException(
    message: String,
    cause: Throwable? = null,
    @StringRes val localMessageResId: Int? = null,
    val failureResponse: ErrorResponse? = null
) : Exception(message, cause)