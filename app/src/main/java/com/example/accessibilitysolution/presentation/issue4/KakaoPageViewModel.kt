package com.example.accessibilitysolution.presentation.issue4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KakaoPageViewModel : ViewModel() {


    private var _isSelectedSystemBrighter = MutableLiveData<Boolean>() // 마이페이지에서 어떤 목록인지
    val isSelectedSystemBrighter : LiveData<Boolean>
        get() = _isSelectedSystemBrighter


    init {
        _isSelectedSystemBrighter.value = false
    }

   fun setSystemBrighter() {
       _isSelectedSystemBrighter.value = !_isSelectedSystemBrighter.value!!
   }


}