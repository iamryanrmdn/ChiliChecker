package com.capstone.chilichecker.view.information

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.chilichecker.R
import com.capstone.chilichecker.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        backButton()
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }
}