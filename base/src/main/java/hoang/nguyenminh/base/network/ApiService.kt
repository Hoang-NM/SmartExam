package hoang.nguyenminh.base.network

import hoang.nguyenminh.base.serializer.Serializer
import retrofit2.HttpException

inline infix fun <reified T> HttpException.safeDeserialize(serializer: Serializer): T? = try {
    response()?.errorBody()?.string()?.let {
        serializer.deserialize(it, T::class.java)
    }
} catch (exception: Exception) {
    null
}