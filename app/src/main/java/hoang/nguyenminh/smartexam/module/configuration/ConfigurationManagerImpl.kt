package hoang.nguyenminh.smartexam.module.configuration

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.util.primitiveDataStore
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("configuration")

class ConfigurationManagerImpl @Inject constructor(
    context: Context, val serializer: Serializer
) : ConfigurationManager {

    private val preferences = context.dataStore

    private companion object {
        val KEY_OF_EXAM = stringPreferencesKey("KEY_OF_EXAM")
    }

    private var prefOfExam: String? by primitiveDataStore(
        preferences, KEY_OF_EXAM, null
    )

    override fun saveCurrentExam(exam: String) {
        prefOfExam = exam
    }

    override fun saveCurrentAnswer() {
        TODO("Not yet implemented")
    }

    override fun getUnfinishedExam(): String? = prefOfExam
}