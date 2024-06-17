package com.capstone.chilichecker.view.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilichecker.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        backButton()
        settingLanguages()
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }

    private fun settingLanguages() {
        binding.chooseLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
}