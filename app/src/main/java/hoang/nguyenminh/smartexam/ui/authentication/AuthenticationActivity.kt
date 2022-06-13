package hoang.nguyenminh.smartexam.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.databinding.ActivityAuthenticationBinding

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private var binding: ActivityAuthenticationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityAuthenticationBinding>(
            this, R.layout.activity_authentication
        ).apply {
            binding = this

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.addOnDestinationChangedListener { navController, destination, args ->
                lblTitle.text = destination.label
            }

            imgNavigation.setOnClickListener {
                onBackPressed()
            }

            setSupportActionBar(toolBar.also {
                it.navigationIcon = null
            })
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(false)
                setDisplayShowHomeEnabled(false)
                setDisplayShowTitleEnabled(false)
            }

        }
    }
}