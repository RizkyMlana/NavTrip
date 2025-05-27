package com.solapp.navtrip.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.solapp.navtrip.R
import com.solapp.navtrip.ui.MainActivity
import com.solapp.navtrip.ui.ViewModelFactory
import com.solapp.navtrip.ui.login.LoginActivity
import com.solapp.navtrip.ui.login.LoginViewModel
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_welcome)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        lifecycleScope.launch {
            viewModel.getSession().collect { user ->
                if (user.isLogin) {
                    // Jika sudah login, langsung ke MainActivity
                    val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }

        val btnNext = findViewById<ImageView>(R.id.imagearrow)
        btnNext.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
