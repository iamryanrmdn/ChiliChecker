package com.capstone.chilichecker.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilichecker.R
import com.capstone.chilichecker.view.MainViewModelFactory
import com.capstone.chilichecker.view.main.MainActivity
import com.capstone.chilichecker.view.main.MainViewModel
import com.capstone.chilichecker.view.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        setupSession()
    }

    private fun setupSession() {
        mainViewModel.getSession().observe(this@SplashScreenActivity) {
            token = it.token
            if (!it.isLogin) {
                moveActivity()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun moveActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}