package com.momoui.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.momoui.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Set up observer for startQuiz LiveData
        viewModel.startQuiz.observe(this) { startQuiz ->
            if (startQuiz) {
                startQuizQuestionsActivity()
                // Reset startQuiz LiveData after starting the quiz
                viewModel.resetStartQuiz()
            }
        }


        viewModel.showToastMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                // Clear the toast message after showing
                viewModel.clearToastMessage()
            }
        }


        binding.etName.addTextChangedListener { text ->
            viewModel.setUserName(text.toString())
        }
    }

    private fun startQuizQuestionsActivity() {
        val intent = Intent(this, QuizQuestionsActivity::class.java)
        intent.putExtra(Constants.USER_NAME, viewModel.userName.value)
        startActivity(intent)
    }
}
