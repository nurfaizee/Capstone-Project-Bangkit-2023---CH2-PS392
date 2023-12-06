package com.dicoding.bottomnavigationbar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.FragmentProfileBinding
import com.dicoding.bottomnavigationbar.ui.login.GetStartedActivity
import com.dicoding.bottomnavigationbar.ui.login.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var binding : FragmentProfileBinding? =null
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(inflater,container, false)
        auth = Firebase.auth

        binding?.btnSignOut?.setOnClickListener{
            if(auth.currentUser != null)
            {
                try {
                    auth.signOut()
                    val intent = Intent(requireContext(), SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } catch (e: Exception) {
                    // Handle the exception, e.g., log it or show a message to the user
                    Log.e("ProfileFragment", "Error signing out: ${e.message}")
                }
//                auth.signOut()
//                startActivity(Intent(requireContext(), SignInActivity::class.java))
            }
        }
//        binding?.btnDashboard?.setOnClickListener {
//            finish()
//        }
//        binding?.btnMap?.setOnClickListener {
//            startActivity(Intent(this,MapActivity::class.java))
//            finish()
//        }
//
//        binding?.cvManageProfile?.setOnClickListener {
//            startActivity(Intent(this,ManageProfileActivity::class.java))
//        }
//        binding?.ivBackBtn?.setOnClickListener {
//            finish()
//        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}