package hoang.nguyenminh.smartexam.module.credential

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.util.primitiveDataStore
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("credential")

class CredentialManagerImpl @Inject constructor(
    context: Context, serializer: Serializer
) : CredentialManager {

    private val preferences = context.dataStore

    private companion object {
        val KEY_OF_TOKEN = stringPreferencesKey("KEY_OF_TOKEN")
    }

    private var prefOfToken: String? by primitiveDataStore(
        preferences, KEY_OF_TOKEN, null
    )

    override fun saveToken(token: String?) {
        prefOfToken = token
    }

    override fun getToken(): String? = prefOfToken
}