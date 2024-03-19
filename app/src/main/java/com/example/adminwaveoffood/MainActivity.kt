package com.example.adminwaveoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminwaveoffood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // xu ly su kien them san pham
        binding.btnAddMenu.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }

        // su kien vao allItem
        binding.allItem.setOnClickListener{
            startActivity(Intent(this, AllItemActivity::class.java))
        }
    }
}