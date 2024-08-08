package com.momoui.quizapp

import android.app.Application
import androidx.lifecycle.*

class QuizViewModel(val userName: String, application: Application) : AndroidViewModel(application) {
    private val questionsList: ArrayList<Question> = Constants.getQuestions()
    val totalQuestions = questionsList.size

    private val _currentQuestionIndex = MutableLiveData(0)
    private val _selectedOptionIndex = MutableLiveData(-1)
    private val _isAnswerChecked = MutableLiveData(false)
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    val currentQuestion: LiveData<Question> = _currentQuestionIndex.map { questionsList[it] }
    val submitButtonText: LiveData<String> = _isAnswerChecked.map {
        if (it) {
            if (_currentQuestionIndex.value == questionsList.size - 1) "FINISH" else "NEXT QUESTION"
        } else "SUBMIT"
    }
    val progressText: LiveData<String> = _currentQuestionIndex.map { "${it + 1}/$totalQuestions" }
    val progressValue: LiveData<Int> = _currentQuestionIndex.map { it + 1 }

    private val _navigateToResult = MutableLiveData(false)
    val navigateToResult: LiveData<Boolean> = _navigateToResult

    private val _showToastMessage = MutableLiveData<String>()
    val showToastMessage: LiveData<String> = _showToastMessage

    fun onOptionSelected(index: Int) {
        if (!_isAnswerChecked.value!!) {
            _selectedOptionIndex.value = index
        }
    }

    fun onSubmitClick() {
        if (!_isAnswerChecked.value!!) {
            checkAnswer()
        } else {
            moveToNextQuestion()
        }
    }

    private fun checkAnswer() {
        val currentQuestion = currentQuestion.value!!
        if (_selectedOptionIndex.value == currentQuestion.correctAnswerIndex) {
            _score.value = _score.value!! + 1
            _showToastMessage.value = "Correct Answer!"
        } else {
            _showToastMessage.value = "Wrong Answer!"
        }
        _isAnswerChecked.value = true
    }

    private fun moveToNextQuestion() {
        if (_currentQuestionIndex.value!! < questionsList.size - 1) {
            _currentQuestionIndex.value = _currentQuestionIndex.value!! + 1
            _selectedOptionIndex.value = -1
            _isAnswerChecked.value = false
        } else {
            _navigateToResult.value = true
        }
    }
}
