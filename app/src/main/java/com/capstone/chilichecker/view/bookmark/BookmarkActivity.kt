package com.capstone.chilichecker.view.bookmark

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilichecker.databinding.ActivityBookmarkBinding

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
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