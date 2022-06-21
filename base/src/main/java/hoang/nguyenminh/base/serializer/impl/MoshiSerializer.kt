package hoang.nguyenminh.base.serializer.impl

import com.squareup.moshi.Moshi
import hoang.nguyenminh.base.serializer.Serializer
import java.lang.reflect.Type

class MoshiSerializer constructor(
    private val moshi: Moshi = Moshi.Builder().build()
) : Serializer {

    override fun serialize(source: Any): String = moshi.adapter(Any::class.java).toJson(source)

    override fun <T> deserialize(source: String, clazz: Class<T>): T? =
        moshi.adapter(clazz).fromJson(source)

    override fun <T> deserialize(source: String, typeOfT: Type): T? =
        moshi.adapter<T>(typeOfT).fromJson(source)
}