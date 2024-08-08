package com.momoui.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _startQuiz = MutableLiveData<Boolean>()
    val startQuiz: LiveData<Boolean> = _startQuiz

    private val _showToastMessage = MutableLiveData<String>()
    val showToastMessage: LiveData<String> = _showToastMessage

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun onStartButtonClick() {
        if (_userName.value.isNullOrEmpty()) {
            _showToastMessage.value = "Please enter your name first"
        } else {
            _startQuiz.value = true
        }
    }

    fun resetStartQuiz() {
        _startQuiz.value = false
    }

    fun clearToastMessage() {
        _showToastMessage.value = null
    }
}
