package com.dicoding.bottomnavigationbar.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.ActivitySignInBinding
import com.dicoding.bottomnavigationbar.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.app.Activity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth

class SignInActivity : BaseActivity() {

    private var binding : ActivitySignInBinding? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding?.tvRegister?.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding?.tvForgotPassword?.setOnClickListener{
            startActivity(Intent(this,ForgetPasswordActivity::class.java))
        }

        binding?.btnSignIn?.setOnClickListener {
            signInUser()
        }

        binding?.btnSignInWithGoogle?.setOnClickListener{
            signInWithGoogle()
        }

    }

    private fun signInUser(){
        val email = binding?.etSinInEmail?.text.toString()
        val password =  binding?.etSinInPassword?.text.toString()
        if (validateForm(email, password))
        {
            showProgressBar()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        startActivityIfNeeded(Intent(this, MainActivity::class.java), 0)
                        hideProgressBar()
//                        startActivity(Intent(this,MainActivity::class.java))
//                        finish()
//                        hideProgressBar()
                    }
                    else
                    {
                        showToast(this, "Can't Login Currently. Try Again Later")
                        hideProgressBar()
                    }
                }
        }

    }

    private fun signInWithGoogle()
    {
        val signInIntent = googleSignInClient.signInIntent
        if(signInIntent != null){
            launcher.launch(signInIntent)
        } else {
            showToast(this, "Google Sign In Failed.Please try again")
        }

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>){
        if (task.isSuccessful)
        {
            val account:GoogleSignInAccount? = task.result
            if (account!=null){
                updateUI(account)
            } else {
                showToast(this, "Google Sign-In failed. Please try again.")
            }
        }
        else
        {
            showToast(this, "Sign In Failed, Try Again Later")
        }
    }

    private fun updateUI(account: GoogleSignInAccount){
        showProgressBar()
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful)
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                hideProgressBar()
            }
            else
            {
                showToast(this, "Can't login currently, Try after sometime")
                hideProgressBar()
            }
        }
    }

    private fun validateForm(email:String,password:String):Boolean
    {
        return when {
            TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmail?.error = "Enter valid email address"
                false
            }
            TextUtils.isEmpty(password)->{
                binding?.tilPassword?.error = "Enter password"
                false
            }
            else -> {
                binding?.tilEmail?.error = null
                true
            }
        }
    }
}