package hoang.nguyenminh.smartexam

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SmartExamApplication : Application() {

    companion object {
        private lateinit var instance: SmartExamApplication

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        FirebaseApp.initializeApp(this)
    }

}

fun appInstance() = SmartExamApplication.getInstance()