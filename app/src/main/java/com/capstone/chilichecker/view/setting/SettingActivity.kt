package com.capstone.chilichecker.view.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.capstone.chilichecker.data.pref.UserPreference
import com.capstone.chilichecker.data.pref.dataStore
import com.capstone.chilichecker.databinding.ActivitySettingBinding
import com.capstone.chilichecker.view.SettingViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        settingPreference()
        backButton()
        settingLanguages()
    }

    private fun settingPreference() {
        val pref = UserPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        ).get(SettingViewModel::class.java)

        settingViewModel.getThemeSetting().observe(this@SettingActivity) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            binding.switchTheme.isChecked = isChecked
            settingViewModel.saveThemeSetting(isChecked)
        }
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