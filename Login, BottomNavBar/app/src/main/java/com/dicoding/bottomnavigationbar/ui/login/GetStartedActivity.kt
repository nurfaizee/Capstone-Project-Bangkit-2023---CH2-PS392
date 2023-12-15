package com.dicoding.bottomnavigationbar.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.LoginManager
import com.dicoding.bottomnavigationbar.databinding.ActivityGetStartedBinding
import com.dicoding.bottomnavigationbar.ui.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GetStartedActivity : AppCompatActivity() {

    private var binding : ActivityGetStartedBinding? = null
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        loginManager = LoginManager(this)

        binding?.cvGetStarted?.setOnClickListener{
            lifecycleScope.launch {
                val email = loginManager.emailFlow.first()
                val accessToken = loginManager.accessTokenFlow.first()

                if (!email.isNullOrBlank() && !accessToken.isNullOrBlank()) {
                    startActivity(Intent(this@GetStartedActivity, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@GetStartedActivity, SignInActivity::class.java))
                    finish()
                }
            }
        }
        val auth = Firebase.auth
        if(auth.currentUser != null)
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}