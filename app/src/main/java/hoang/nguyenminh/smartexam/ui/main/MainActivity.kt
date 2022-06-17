package hoang.nguyenminh.smartexam.ui.main

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseActivity
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.databinding.ActivityMainBinding
import hoang.nguyenminh.smartexam.util.BindingAdapters.viewCompatVisibility

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val viewModel by viewModels<MainViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var binding: ActivityMainBinding? = null

    override fun onCreateViewDataBinding(): ViewDataBinding =
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            binding = this

            val primaryDestination =
                listOf(R.id.fragment_home, R.id.fragment_exam, R.id.fragment_profile)

            appbar.apply {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

                val navController = navHostFragment.navController.apply {
                    addOnDestinationChangedListener { _, destination, _ ->
                        lblTitle.text = destination.label
                        (previousBackStackEntry == null).let {
                            imgNavigation.viewCompatVisibility(!it && destination.id !in primaryDestination)
                            bottomNavigation.viewCompatVisibility(destination.id in primaryDestination)
                        }
                    }
                }

                NavigationUI.setupWithNavController(bottomNavigation, navController)

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