package hoang.nguyenminh.base.serializer

import com.google.gson.JsonElement
import java.lang.reflect.Type

interface Serializer {
    fun toJsonTree(source: Any): JsonElement
    fun serialize(source: Any): String
    fun <T> deserialize(source: String, clazz: Class<T>): T
    fun <T> deserialize(source: String, typeOfT: Type): T
}

inline fun <reified T> Serializer.deserialize(source: String): T {
    return this.deserialize(source, T::class.java)
}