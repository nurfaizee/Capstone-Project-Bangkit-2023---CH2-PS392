package com.dicoding.bottomnavigationbar.ui.splash

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.bottomnavigationbar.data.LoginManager
import com.dicoding.bottomnavigationbar.databinding.ActivitySplashScreenBinding
import com.dicoding.bottomnavigationbar.ui.login.SignInActivity
import com.dicoding.bottomnavigationbar.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var loginManager: LoginManager
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        playAnimation()

        auth = FirebaseAuth.getInstance()
        loginManager = LoginManager(this)

        Handler().postDelayed({
            checkLoginStatus()
        }, DURATION.toLong())
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            val email = loginManager.emailFlow.first()
            val accessToken = loginManager.accessTokenFlow.first()

            val intent = if (!email.isNullOrBlank() && !accessToken.isNullOrBlank()) {
                Intent(this@SplashScreen, MainActivity::class.java)
            } else {
                if (auth.currentUser != null) {
                    Intent(this@SplashScreen, MainActivity::class.java)
                } else {
                    Intent(this@SplashScreen, SignInActivity::class.java)
                }
            }

            startActivity(intent)
            finish()
        }
    }

    private fun playAnimation(){
        val logo = binding.ivSplash
        ObjectAnimator.ofFloat(logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = DURATION.toLong()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    companion object {
        private const val DURATION = 4000
    }

}
