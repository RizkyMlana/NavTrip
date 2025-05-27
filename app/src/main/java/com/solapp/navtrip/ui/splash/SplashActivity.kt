package com.solapp.navtrip.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.solapp.navtrip.R
import com.solapp.navtrip.ui.MainActivity
import com.solapp.navtrip.ui.MainViewModel
import com.solapp.navtrip.ui.ViewModelFactory
import com.solapp.navtrip.ui.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupView()

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getSession().observe(this) { user ->
                if (user.isLogin) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                }
                finish()
            }
        }, 1000)  // Delay 1 detik agar splash screen tetap terlihat

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}