package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminwaveoffood.databinding.ActivityAdminProfileMainBinding

class AdminProfileMainActivity : AppCompatActivity() {

    private val binding: ActivityAdminProfileMainBinding by lazy {
        ActivityAdminProfileMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}