package com.dicoding.bottomnavigationbar.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.LoginManager
import com.dicoding.bottomnavigationbar.ui.login.GetStartedActivity
import com.dicoding.bottomnavigationbar.ui.login.SignInActivity
import com.dicoding.bottomnavigationbar.ui.main.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        loginManager = LoginManager(this)

        Handler().postDelayed({
            checkLoginStatus()
        }, 2000)
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            val email = loginManager.emailFlow.first()
            val accessToken = loginManager.accessTokenFlow.first()

            val intent = if (!email.isNullOrBlank() && !accessToken.isNullOrBlank()) {
                Intent(this@SplashScreen, MainActivity::class.java)
            } else {
                Intent(this@SplashScreen, SignInActivity::class.java)
            }

            startActivity(intent)
            finish()
        }
    }
}
