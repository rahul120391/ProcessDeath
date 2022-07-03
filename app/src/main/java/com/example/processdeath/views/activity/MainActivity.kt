package com.example.processdeath.views.activity

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import com.example.processdeath.R
import com.example.processdeath.databinding.ActivityMainBinding
import com.example.processdeath.views.base.BaseActivity
import com.example.processdeath.views.extensions.viewBinding
import com.example.processdeath.views.fragments.LoginFragment
import com.example.processdeath.views.fragments.MainFragment
import com.example.processdeath.views.utils.LocalLanguageChangeHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    override val statusBarColor: Int
        get() = Color.TRANSPARENT
    override fun getView(): View = binding.root

    private val _onLanguageChange = MutableLiveData<Context>()
    fun languageChange():LiveData<Context> = _onLanguageChange
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }
            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.let { LocalLanguageChangeHelper.onAttach(it) })
    }



    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val currentFragment = navHostFragment.childFragmentManager.fragments.first()
        if(currentFragment is LoginFragment || currentFragment is MainFragment){
            finish()
        }
        else{
            super.onBackPressed()
        }

    }

}