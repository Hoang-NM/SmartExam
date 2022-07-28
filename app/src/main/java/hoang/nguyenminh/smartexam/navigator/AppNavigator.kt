package hoang.nguyenminh.smartexam.navigator

import android.app.Activity
import android.content.Intent
import hoang.nguyenminh.smartexam.ui.authentication.AuthenticationActivity
import hoang.nguyenminh.smartexam.ui.main.MainActivity

object AppNavigator {

    fun Activity.toAuthentication(finish: Boolean) {
        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        if (finish) finish()
    }

    fun Activity.toMain(finish: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        if (finish) finish()
    }
}