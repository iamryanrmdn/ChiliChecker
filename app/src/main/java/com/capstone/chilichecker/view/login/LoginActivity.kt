package com.capstone.chilichecker.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.databinding.ActivityLoginBinding
import com.capstone.chilichecker.view.UserViewModelFactory
import com.capstone.chilichecker.view.main.MainActivity
import com.capstone.chilichecker.view.signup.SignupActivity

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel> {
        UserViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        goToSignup()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            showLoading()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginViewModel.login(email, password)
            loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
                loginViewModel.saveSession(
                    UserModel(
                        response.loginResult?.name.toString(),
                        response.loginResult?.token.toString(),
                        true
                    )
                )
            }

            loginViewModel.toastText.observe(this@LoginActivity) {
                it.getContentIfNotHandled()?.let { toastText ->
                    Toast.makeText(
                        this@LoginActivity, toastText, Toast.LENGTH_SHORT
                    ).show()
                }
            }

            loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
                if (response.error == false) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Success!")
                        setMessage("Login successful! Let's scan your chili!!!")
                        setPositiveButton("Continue") { _, _ ->
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun goToSignup() {
        binding.labelSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading() {
        loginViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}