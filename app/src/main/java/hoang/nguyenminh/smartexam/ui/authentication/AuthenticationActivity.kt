package hoang.nguyenminh.smartexam.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatVisibility
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamActivity
import hoang.nguyenminh.smartexam.databinding.ActivityAuthenticationBinding
import hoang.nguyenminh.smartexam.ui.main.MainActivity

@AndroidEntryPoint
class AuthenticationActivity : SmartExamActivity<ActivityAuthenticationBinding>() {

    override val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isLoading
            }
        }

//        if (viewModel.flowOfAuthenticationInfo.value != null) {
//            val intent = Intent(baseContext, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateViewDataBinding(): ActivityAuthenticationBinding =
        DataBindingUtil.setContentView<ActivityAuthenticationBinding>(
            this, R.layout.activity_authentication
        ).apply {
            binding = this

            appbar.apply {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

                navHostFragment.navController.apply {
                    addOnDestinationChangedListener { _, destination, _ ->
                        lblTitle.text = destination.label
                        (previousBackStackEntry == null).let {
                            imgNavigation.viewCompatVisibility(!it)
                        }
                    }
                }

                imgNavigation.setOnClickListener {
                    onBackPressed()
                }

                setSupportActionBar(toolBar.also {
                    it.navigationIcon = null
                })
            }

            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(false)
                setDisplayShowHomeEnabled(false)
                setDisplayShowTitleEnabled(false)
            }
        }
}