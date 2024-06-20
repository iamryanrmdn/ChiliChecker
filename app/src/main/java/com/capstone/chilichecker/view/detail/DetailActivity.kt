package com.capstone.chilichecker.view.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.chilichecker.data.pref.BookmarkModel
import com.capstone.chilichecker.databinding.ActivityDetailBinding
import com.capstone.chilichecker.view.PredictViewModelFactory
import com.capstone.chilichecker.view.bookmark.BookmarkViewModel
import com.capstone.chilichecker.view.scan.ScanActivity

class DetailActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel> {
        PredictViewModelFactory.getInstance(this)
    }

    private val bookmarkViewModel by viewModels<BookmarkViewModel>()

    private lateinit var binding: ActivityDetailBinding
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDetail()
        supportActionBar?.hide()
        backButton()
        scanAgainButton()
        addToBookmark()
    }

    private fun setupDetail() {
        detailViewModel.getSession().observe(this) {
            token = it.token
        }
        val label = intent.getStringExtra("label")
        val description = intent.getStringExtra("description")
        val marketPrice = intent.getStringExtra("marketPrice")
        val careInstructions = intent.getStringExtra("careInstructions")
        val name = intent.getStringExtra("name")
        val suitableDishes = intent.getStringExtra("suitableDishes")
        val image = intent.getStringExtra("image")

        binding.apply {
            textResult.text = label
            textDescription.text = description
            textPrice.text = marketPrice
            textDescCare.text = careInstructions
            textDescName.text = name
            textDescSuitable.text = suitableDishes
            image?.let {
                Glide.with(this@DetailActivity)
                    .load(Uri.parse(it))
                    .into(imgResult)
            }
        }
    }

    private fun addToBookmark() {
        binding.btnAddToBookmark.setOnClickListener {
            val bookmark = BookmarkModel(
                label = binding.textResult.text.toString(),
                description = binding.textDescription.text.toString(),
                marketPrice = binding.textPrice.text.toString(),
                careInstructions = binding.textDescCare.text.toString(),
                name = binding.textDescName.text.toString(),
                suitableDishes = binding.textDescSuitable.text.toString(),
                image = intent.getStringExtra("image").toString()
            )
            bookmarkViewModel.addBookmark(bookmark)
            showToast("Bookmark added successfully")
        }
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun scanAgainButton() {
        binding.btnUploadAgain.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ScanActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}