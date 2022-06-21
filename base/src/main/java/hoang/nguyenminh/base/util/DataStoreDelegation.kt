package hoang.nguyenminh.base.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
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

fun <T> primitiveDataStore(
    preferences: DataStore<Preferences>,
    prefKey: Preferences.Key<T>,
    defaultValue: T?
) = PrimitiveTypeDataStore(preferences, prefKey, defaultValue)