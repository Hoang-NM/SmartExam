package hoang.nguyenminh.smartexam.service.preference

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore("credential")

class CredentialManager {
}