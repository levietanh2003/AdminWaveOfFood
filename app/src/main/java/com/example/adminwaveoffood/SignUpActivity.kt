package com.example.adminwaveoffood

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.ArrayAdapter
import com.example.adminwaveoffood.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // su ly khi da co tai khoan
        binding.alreadyHaveAnAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


        val locationList = arrayOf("HCM",  "Can Tho", "Ca Noi", "Quang Ngai", "Da Nang")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, locationList)

        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)


        //  thao tac su kien an hien  mat khau
        val editText = binding.editTextTextPassword
//        val eyeHideDrawable = ContextCompat.getDrawable(this, R.drawable.arrow_down_float)
//        val eyeShowDrawable = ContextCompat.getDrawable(this, R.drawable.btn_dialog)
        val eyeHideDrawable = applicationContext.resources.getDrawable(R.drawable.arrow_down_float, null)
        val eyeShowDrawable = applicationContext.resources.getDrawable(R.drawable.btn_dialog, null)

        editText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = 2 // index của drawableRight
                if (event.rawX >= editText.right - editText.compoundDrawables[drawableRight].bounds.width()) {
                    // Xử lý sự kiện khi click vào drawableRight
                    if (editText.transformationMethod == PasswordTransformationMethod.getInstance()) {
                        // Đang ẩn mật khẩu, cần hiển thị
                        editText.transformationMethod = null
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeHideDrawable, null)
                    } else {
                        // Đang hiển thị mật khẩu, cần ẩn đi
                        editText.transformationMethod = PasswordTransformationMethod.getInstance()
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeShowDrawable, null)
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}