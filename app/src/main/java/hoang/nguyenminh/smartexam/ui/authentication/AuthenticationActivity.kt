package hoang.nguyenminh.smartexam.ui.authentication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseActivity
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatVisibility
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.databinding.ActivityAuthenticationBinding

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity() {

    override val viewModel by viewModels<AuthenticationViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var binding: ActivityAuthenticationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateViewDataBinding(): ViewDataBinding =
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}