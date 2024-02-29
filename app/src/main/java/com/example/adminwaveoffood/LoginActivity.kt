package com.example.adminwaveoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminwaveoffood.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // luong xu ly don't have account
        binding.doHaveAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // luong xu ly login
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}