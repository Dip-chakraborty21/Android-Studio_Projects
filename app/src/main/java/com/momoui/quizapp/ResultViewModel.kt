package com.momoui.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModel(
    val userName: String,
    val totalQuestions: Int,
    val score: Int
) : ViewModel() {

    val congratulationsText = "Congratulations, $userName!"
    val scoreText = "Your score is $score out of $totalQuestions"

    private val _restartQuiz = MutableLiveData<Boolean>()
    val restartQuiz: LiveData<Boolean> = _restartQuiz

    fun onRestartClick() {
        _restartQuiz.value = true
    }
}

class ResultViewModelFactory(
    private val userName: String,
    private val totalQuestions: Int,
    private val score: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(userName, totalQuestions, score) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}