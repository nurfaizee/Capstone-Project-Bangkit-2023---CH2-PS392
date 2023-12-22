package com.dicoding.bottomnavigationbar.ui.stunting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bottomnavigationbar.data.retrofit.StuntingResponse

class StuntingViewModel : ViewModel() {

    private val _stuntingResponse = MutableLiveData<StuntingResponse>()
    val stuntingResponse: LiveData<StuntingResponse> get() = _stuntingResponse
}