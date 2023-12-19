package com.dicoding.bottomnavigationbar.ui.stunting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bottomnavigationbar.ui.Retrofit.DeteksiStuntingRequest
import com.dicoding.bottomnavigationbar.ui.Retrofit.Retro
import com.dicoding.bottomnavigationbar.ui.Retrofit.StuntingRequest
import com.dicoding.bottomnavigationbar.ui.Retrofit.StuntingResponse
import com.dicoding.bottomnavigationbar.ui.Retrofit.UserApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class StuntingViewModel : ViewModel() {
    private val _stuntingResponse = MutableLiveData<StuntingResponse>()
    val stuntingResponse: LiveData<StuntingResponse> get() = _stuntingResponse

    fun sendData(jenisKelamin: String, tinggiBadan: Int,beratBadan: Int,  usia: Int) {
        val retro = Retro().getRetroClientInstanceStunting().create(UserApi::class.java)
        val request= StuntingRequest( jenisKelamin,tinggiBadan,beratBadan,  usia)

            retro.deteksiStunting(request).enqueue(object : Callback<StuntingResponse> {
            override fun onResponse(call: Call<StuntingResponse>, response: Response<StuntingResponse>) {
                if (response.isSuccessful) {
                    val responseData = response.body()

                    if (responseData != null) {
                        // Update LiveData with the response
                        _stuntingResponse.value = responseData
//                        main()
                    } else {
                        // Handle null response data
                    }
                } else {
                    // Handle unsuccessful response
                    // ...
                }
            }
            override fun onFailure(call: Call<StuntingResponse>, t: Throwable) {
                // Handle network request failure
                // ...
            }
        })
    }


    // Other functions...
}