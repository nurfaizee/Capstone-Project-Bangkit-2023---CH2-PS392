package com.dicoding.bottomnavigationbar.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.ui.login.GetStartedActivity
import com.dicoding.bottomnavigationbar.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        Handler().postDelayed({
            startActivity(Intent(this, GetStartedActivity::class.java))
            finish()
        }, 2000)
    }
}