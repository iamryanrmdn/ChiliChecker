package com.capstone.chilichecker.view.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilichecker.R
import com.capstone.chilichecker.databinding.ActivityMainBinding
import com.capstone.chilichecker.view.MainViewModelFactory
import com.capstone.chilichecker.view.bookmark.BookmarkActivity
import com.capstone.chilichecker.view.information.InformationActivity
import com.capstone.chilichecker.view.maps.MapsActivity
import com.capstone.chilichecker.view.scan.ScanActivity
import com.capstone.chilichecker.view.setting.SettingActivity
import com.capstone.chilichecker.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        menuBar()
        navigation()
        setupSession()
    }

    override fun onResume() {
        super.onResume()
        setupData()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menu_setting -> {
//                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
//                true
//            }
//
//            R.id.menu_logout -> {
//                //Coming Soon
//                finish()
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun menuBar() {
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_setting -> {
                    startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                    true
                }

                R.id.menu_logout -> {
                    mainViewModel.logout()
                    mainViewModel.getSession().observe(this@MainActivity) {
                        Log.d(TAG, "Token: ${it.token}")
                        Log.d(TAG, "Email: ${it.email}")
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun setupSession() {
        mainViewModel.getSession().observe(this@MainActivity) {
            token = it.token
            Log.d(TAG, "setupSession: $token")
            if (!it.isLogin) {
                moveActivity()
            } else {
                setupData()
            }
        }
    }

    private fun moveActivity() {
        startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
        finish()
    }

    private fun setupData() {
        showLoading()
        mainViewModel.getSession().observe(this@MainActivity) {
            token = "Bearer " + it.token
            Log.d(TAG, "setupSession: $token")
            Log.d(TAG, "setupName: ${it.email}")
        }
    }

    private fun navigation() {
        binding.btnScanChili.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnBookmark.setOnClickListener {
            val intent = Intent(this, BookmarkActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnGeoChili.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnInformation.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading() {
        mainViewModel.isLoading.observe(this@MainActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}