package com.capstone.chilichecker.view.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilichecker.R
import com.capstone.chilichecker.databinding.ActivityScanBinding
import com.capstone.chilichecker.di.getImageUri
import com.capstone.chilichecker.di.reduceFileImage
import com.capstone.chilichecker.di.uriToFile
import com.capstone.chilichecker.view.PredictViewModelFactory
import com.capstone.chilichecker.view.result.ResultActivity

class ScanActivity : AppCompatActivity() {

    private val scanViewModel by viewModels<ScanViewModel> {
        PredictViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityScanBinding
    private var currentImageUri: Uri? = null
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnScan.setOnClickListener {
            uploadImage()
        }

        supportActionBar?.hide()
        backButton()
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No Media Selected")
        }
    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imgScan.setImageURI(it)
        }
    }

    private fun uploadImage() {
        scanViewModel.getSession().observe(this) {
            token = "Bearer " + it.token
        }
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            showLoading()
            scanViewModel.uploadImage(imageFile)
            scanViewModel.uploadImageResponse.observe(this@ScanActivity) { response ->
                if (response.status == "success") {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }

    private fun showLoading() {
        scanViewModel.isLoading.observe(this@ScanActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}