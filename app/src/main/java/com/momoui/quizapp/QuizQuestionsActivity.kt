package com.momoui.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.momoui.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionsBinding
    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_questions)

        // Initialize ViewModel with a factory
        val userName = intent.getStringExtra(Constants.USER_NAME) ?: ""
        val viewModelFactory = QuizViewModelFactory(userName, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizViewModel::class.java)

        // Set up Data Binding
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Set up click listeners for options
        setupOptionClickListeners()

        // Observe LiveData to navigate to ResultActivity
        viewModel.navigateToResult.observe(this) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToResultActivity()
            }
        }

        // Observe LiveData for Toast messages
        viewModel.showToastMessage.observe(this) { message ->
            showToast(message)
        }
    }

    private fun setupOptionClickListeners() {
        val optionViews = listOf(
            binding.optionOne,
            binding.optionTwo,
            binding.optionThree,
            binding.optionFour
        )

        optionViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.onOptionSelected(index)
                updateOptionSelection(index, optionViews)
            }
        }
    }
    private fun updateOptionSelection(selectedIndex: Int, optionViews: List<TextView>) {
        optionViews.forEachIndexed { index, textView ->
            if (index == selectedIndex) {
                textView.setTextColor(ContextCompat.getColor(this, R.color.selected_option_text))
                textView.setBackgroundResource(R.drawable.option_selected_background)
            } else {
                textView.setTextColor(ContextCompat.getColor(this, R.color.default_option_text))
                textView.setBackgroundResource(R.drawable.option_default_background)
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToResultActivity() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(Constants.USER_NAME, viewModel.userName)
            putExtra(Constants.TOTAL_QUESTIONS, viewModel.totalQuestions)
            putExtra(Constants.SCORE, viewModel.score.value)
        }
        startActivity(intent)
        finish()
    }
}
