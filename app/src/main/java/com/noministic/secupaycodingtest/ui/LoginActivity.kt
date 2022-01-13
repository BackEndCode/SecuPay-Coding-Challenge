package com.noministic.secupaycodingtest.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.noministic.secupaycodingtest.databinding.ActivityLoginBinding
import com.noministic.secupaycodingtest.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        setViewClickListners()
    }

    private fun observeViewModel() {
        viewModel.userLoggedIn.observe(this, {
            if (it) openMainApp()
        })
        viewModel.errorMessage.observe(this, {
            if (it.isNotEmpty())
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun openMainApp() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun setViewClickListners() {
        binding.cirLoginButton.setOnClickListener {
            viewModel.authenticateUser(
                binding.edittextUsername.text.toString(),
                binding.edittextPassword.text.toString()
            )
        }
    }
}