package com.capstone.chilichecker.view.signup

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
import com.capstone.chilichecker.databinding.ActivitySignupBinding
import com.capstone.chilichecker.view.UserViewModelFactory
import com.capstone.chilichecker.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private val signupViewModel by viewModels<SignupViewModel> {
        UserViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        goToLogin()
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
        binding.btnSignup.setOnClickListener {
            showLoading()
            val name = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            signupViewModel.register(name, email, password)
            signupViewModel.toastText.observe(this@SignupActivity) {
                it.getContentIfNotHandled()?.let { toastText ->
                    Toast.makeText(
                        this@SignupActivity, toastText, Toast.LENGTH_SHORT
                    ).show()
                }
            }

            signupViewModel.registerResponse.observe(this@SignupActivity) { response ->
                if (response.error == false) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Success!")
                        setMessage("Congratulations, your account $email has been successfully created")
                        setPositiveButton("Continue") { _, _ ->
                            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun goToLogin() {
        binding.labelLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading() {
        signupViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}