package hoang.nguyenminh.base

import android.app.Application
import android.content.pm.ApplicationInfo

abstract class BaseApplication : Application() {

    companion object {
        private lateinit var instance: BaseApplication

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

fun appInstance() = BaseApplication.getInstance()

fun Application.isDebugMode() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0