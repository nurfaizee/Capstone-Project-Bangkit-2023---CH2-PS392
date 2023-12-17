package com.dicoding.bottomnavigationbar.ui.profileManage

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.ActivityManageProfileBinding
import com.dicoding.bottomnavigationbar.ui.Retrofit.UsersResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Response

class ManageProfileActivity : AppCompatActivity() {
    private var binding: ActivityManageProfileBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.ivBackBtnManageProfile?.setOnClickListener {
            finish()
        }
        setProfilePic()
    }
    private fun setProfilePic()
    {

        val mFireStore = FirebaseFirestore.getInstance()
//        mFireStore.collection(SyncStateContract.Constants.USERS).document(FireStoreClass().getCurrentUserId()).get().addOnSuccessListener { document->
//            val image = document.get("image").toString()
//            Log.e("getImage",image)
//            val storageRef = Firebase.storage.reference
//            val pathReference = storageRef.child(image)
//            val ONE_MEGABYTE: Long = 1024 * 1024
//            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {byteArray->
//                val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

//                binding?.circleImageView?.setImageBitmap(bmp)
//            }
//        }
    }


}