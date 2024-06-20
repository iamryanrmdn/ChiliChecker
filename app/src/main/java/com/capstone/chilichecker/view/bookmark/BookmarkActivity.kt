package com.capstone.chilichecker.view.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.chilichecker.adapter.BookmarkAdapter
import com.capstone.chilichecker.databinding.ActivityBookmarkBinding
import com.capstone.chilichecker.view.main.MainActivity

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private val bookmarkViewModel by viewModels<BookmarkViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        supportActionBar?.hide()
        backButton()
    }

    private fun setupRecyclerView() {
        binding.rvBookmark.layoutManager = LinearLayoutManager(this)
        bookmarkViewModel.bookmarks.observe(this) { bookmark ->
            val adapter = BookmarkAdapter(bookmark)
            binding.rvBookmark.adapter = adapter
        }
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}