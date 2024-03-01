package com.example.adminwaveoffood

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Xử lý sự kiện khi người dùng chọn ảnh
        binding.txtSelectImage.setOnClickListener {
            pickImage.launch("image/*")
        }
        // xu ly su kien back button
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    // Đăng ký hoạt động nhận kết quả từ việc chọn ảnh
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri ->
            // Hiển thị ảnh đã chọn lên ImageView
            binding.selectedImage.setImageURI(imageUri)
        }
    }
}
