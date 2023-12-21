package com.dicoding.bottomnavigationbar.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.lifecycleScope
import com.dicoding.bottomnavigationbar.data.LoginManager
import com.dicoding.bottomnavigationbar.data.retrofit.LoginResponse
import com.dicoding.bottomnavigationbar.databinding.ActivitySignUpBinding
import com.dicoding.bottomnavigationbar.data.retrofit.Retro
import com.dicoding.bottomnavigationbar.data.retrofit.UserApi
import com.dicoding.bottomnavigationbar.data.retrofit.UsersResponse
import com.dicoding.bottomnavigationbar.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    private var binding : ActivitySignUpBinding? = null
    private lateinit var auth : FirebaseAuth
    private val loginManager by lazy { LoginManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.tvLoginPage?.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        binding?.btnSignUp?.setOnClickListener { registerUser()  }

        binding?.seePassword?.setOnCheckedChangeListener { _, isChecked ->
            binding?.etSinUpPassword?.transformationMethod = if (isChecked) {
                HideReturnsTransformationMethod.getInstance()
            } else {
                PasswordTransformationMethod.getInstance()
            }
            binding?.etSinUpPassword?.text?.let { binding?.etSinUpPassword?.setSelection(it.length) }
        }

    }

    private fun registerUser(){
        val name = binding?.etSinUpName?.text.toString()
        val email = binding?.etSinUpEmail?.text.toString()
        val password = binding?.etSinUpPassword?.text.toString()
        if(validateForm(name, email, password))
        {
            val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
            retro.register(username = name,email = email, password = password).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        showProgressBar()
                        val loginResponse = response.body()

                        if (loginResponse != null) {
                            val user = loginResponse.data

                            if (user != null) {
                                lifecycleScope.launch {
                                    loginManager.saveLoginData(user.email ?: "", loginResponse.data.accessToken ?: "", user.username ?: "")
                                }
                                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                                val token = loginResponse.data.accessToken
                                showToast(this@SignUpActivity, "$token")
                                showToast(this@SignUpActivity, "User Id Created Successfully")
                                hideProgressBar()
                                finish()
                                // Process the data
                            } else {
                                showToast(this@SignUpActivity, "User Id Not Created. Try Again Later")
                                hideProgressBar()
                                // Handle null user in the response body
                            }
                        } else {
                            showToast(this@SignUpActivity, "gagal")
                            hideProgressBar()                            // Handle null response body
                        }
                    } else {
                        showToast(this@SignUpActivity, "Email/Nama Data Sudah ada")
                        hideProgressBar()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Handle failure
                    Log.e("Error", t.message!!)
                }
            })
        }
    }

    private fun validateForm(name:String, email:String,password:String):Boolean
    {
        return when {
            TextUtils.isEmpty(name)->{
                binding?.etSinUpName?.error = "Enter name"
                false
            }
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.etSinUpEmail?.error = "Enter valid email address"
                false
            }
            TextUtils.isEmpty(password)->{
                binding?.etSinUpPassword?.error = "Enter password"
                false
            }
            else -> { true }
        }
    }
}