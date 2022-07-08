package hoang.nguyenminh.base.serializer.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import hoang.nguyenminh.base.serializer.Serializer
import java.lang.reflect.Type

class GsonSerializer constructor(
    private val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
) : Serializer {

    override fun toJsonTree(source: Any): JsonElement = gson.toJsonTree(source)

    override fun serialize(source: Any): String = gson.toJson(source)

    override fun <T> deserialize(source: String, clazz: Class<T>): T = gson.fromJson(source, clazz)

    override fun <T> deserialize(source: String, typeOfT: Type): T = gson.fromJson(source, typeOfT)
}