package hoang.nguyenminh.base.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.reflect.TypeToken
import hoang.nguyenminh.base.serializer.Serializer
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrimitiveTypeDataStore<T>(
    private val dataStore: DataStore<Preferences>,
    private val prefKey: Preferences.Key<T>,
    defaultValue: T? = null
) : ReadWriteProperty<Any?, T?> {
    private var backingField: T? = runBlocking {
        dataStore.data.firstOrNull()?.let {
            it[prefKey]
        } ?: defaultValue
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = backingField

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (backingField != value) {
            backingField = value
            runBlocking {
                dataStore.edit {
                    if (value == null) it.remove(prefKey)
                    else it[prefKey] = value
                }
            }
        }
    }
}

class SerializableDataStoreDelegate<T>(
    private val dataStore: DataStore<Preferences>,
    private val serializer: Serializer,
    private val type: Type,
    private val prefKey: Preferences.Key<String>,
    defaultValue: T? = null
) : ReadWriteProperty<Any?, T?> {

    private var backingField: T? = runBlocking {
        dataStore.data.firstOrNull()?.let {
            val plain = it[prefKey]
            if (plain.isNullOrEmpty()) null
            else plain
        }?.let {
            serializer.deserialize(it, type)
        } ?: defaultValue
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = backingField

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (backingField != value) {
            backingField = value
            runBlocking {
                dataStore.edit {
                    if (value == null) it.remove(prefKey)
                    else it[prefKey] = serializer.serialize(value)
                }
            }
        }
    }
}

fun <T> primitiveDataStore(
    preferences: DataStore<Preferences>,
    prefKey: Preferences.Key<T>,
    defaultValue: T?
) = PrimitiveTypeDataStore(preferences, prefKey, defaultValue)

inline fun <reified T> serializableDataStore(
    preferences: DataStore<Preferences>,
    serializer: Serializer,
    prefKey: Preferences.Key<String>,
    defaultValue: T? = null
) = SerializableDataStoreDelegate(
    preferences,
    serializer,
    TypeToken.get(T::class.java).type,
    prefKey,
    defaultValue
)