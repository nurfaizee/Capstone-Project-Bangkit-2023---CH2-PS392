package com.dicoding.bottomnavigationbar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.bottomnavigationbar.data.LoginManager
import com.dicoding.bottomnavigationbar.databinding.FragmentProfileBinding
import com.dicoding.bottomnavigationbar.ui.login.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var binding : FragmentProfileBinding? =null
    private lateinit var auth : FirebaseAuth
    private val loginManager: LoginManager by lazy { LoginManager(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container, false)
        auth = Firebase.auth
        lifecycleScope.launch {
            loginManager.usernameFlow.collect { username ->
                binding?.tvUsername?.text = username ?: "No username available"
            }
        }
        lifecycleScope.launch {
            // Observe username changes
            loginManager.emailFlow.collect { email ->
                binding?.tvEmail?.text = email ?: "No email available"
            }

        }

        val currentUser = auth.currentUser

        currentUser?.let {
            binding?.tvUsername?.text = currentUser.displayName ?: "No username available"
            binding?.tvEmail?.text = currentUser.email ?: "No email available"
        }

        binding?.btnSignOut?.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        return binding?.root
    }


    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { dialog, which ->
            logoutUser()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun logoutUser() {
        lifecycleScope.launch {
            when {
                auth.currentUser != null -> {
                    try {
                        auth.signOut()
                        navigateToSignInActivity()
                    } catch (e: Exception) {
                        Log.e("ProfileFragment", "Error signing out: ${e.message}")
                    }
                }
                loginManager.isUserLoggedIn.first() -> {
                    loginManager.clearLoginData()
                    navigateToSignInActivity()
                }
                else -> {
                    // Handle the case when neither Firebase Auth nor LoginManager has a logged-in user
                    Log.d("ProfileFragment", "No user is logged in.")
                }
            }
        }
    }

    private fun navigateToSignInActivity() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}