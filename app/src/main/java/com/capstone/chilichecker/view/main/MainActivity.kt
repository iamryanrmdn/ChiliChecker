package com.capstone.chilichecker.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilichecker.R
import com.capstone.chilichecker.databinding.ActivityMainBinding
import com.capstone.chilichecker.view.bookmark.BookmarkActivity
import com.capstone.chilichecker.view.information.InformationActivity
import com.capstone.chilichecker.view.maps.MapsActivity
import com.capstone.chilichecker.view.scan.ScanActivity
import com.capstone.chilichecker.view.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        menuBar()
        navigation()
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
                    //Coming Soon
                    finish()
                    true
                }

                else -> false
            }
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
}