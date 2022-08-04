package hoang.nguyenminh.smartexam.util.module.configuration

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.util.serializableDataStore
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("configuration")

class ConfigurationManagerImpl @Inject constructor(
    context: Context, serializer: Serializer
) : ConfigurationManager {

    private val preferences = context.dataStore

    private companion object {
        val KEY_OF_EXAM = stringPreferencesKey("KEY_OF_EXAM")
    }

    private var prefOfExam: ExamModel? by serializableDataStore(
        preferences, serializer, KEY_OF_EXAM, null
    )

    override fun saveCurrentExam(exam: ExamModel) {
        prefOfExam = exam
    }

    override fun saveCurrentAnswer(question: QuestionModel) {
        prefOfExam?.questions?.find {
            it.id == question.id
        }?.choices = question.choices
    }

    override fun getUnfinishedExam(): ExamModel? = prefOfExam

    override fun clearFinishedExam() {
        prefOfExam = null
    }
}