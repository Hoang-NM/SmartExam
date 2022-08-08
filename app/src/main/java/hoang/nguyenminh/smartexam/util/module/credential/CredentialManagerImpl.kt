package hoang.nguyenminh.smartexam.util.module.credential

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.util.primitiveDataStore
import hoang.nguyenminh.base.util.serializableDataStore
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("credential")

class CredentialManagerImpl @Inject constructor(
    context: Context, serializer: Serializer
) : CredentialManager {

    private val preferences = context.dataStore

    private companion object {
        val KEY_OF_TOKEN = stringPreferencesKey("KEY_OF_TOKEN")
        val KEY_OF_AUTHENTICATION = stringPreferencesKey("KEY_OF_AUTHENTICATION")
    }

    private var prefOfToken: String? by primitiveDataStore(
        preferences, KEY_OF_TOKEN, null
    )

    private var prefOfAuthenticationInfo: UserInfo? by serializableDataStore(
        preferences, serializer, KEY_OF_AUTHENTICATION, null
    )

    override fun saveAuthenticationInfo(info: UserInfo) {
        prefOfAuthenticationInfo = info
    }

    override fun getAuthenticationInfo(): UserInfo? = prefOfAuthenticationInfo

    override fun saveToken(token: String?) {
        prefOfToken = token
    }

    override fun getToken(): String? = prefOfToken

    override fun clearAuthenticationInfo() {
        prefOfToken = null
        prefOfAuthenticationInfo = null
    }
}