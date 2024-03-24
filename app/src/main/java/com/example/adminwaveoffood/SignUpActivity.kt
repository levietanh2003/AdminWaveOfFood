package com.example.adminwaveoffood

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminwaveoffood.Model.UserModel
import com.example.adminwaveoffood.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var userName : String
    private lateinit var nameOfRestaurant : String
    private lateinit var database : DatabaseReference

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize firebase
        auth = Firebase.auth
        // initialize database
        database = Firebase.database.reference


        // su ly su kien btn dang ky
        binding.btnSignUp.setOnClickListener {
            // lay du lieu tren form
            email = binding.emailOrPhone.text.toString()
            password = binding.password.text.toString()
            nameOfRestaurant = binding.restaurantName.text.toString()
            userName = binding.nameAccount.text.toString()

            // kiem tra du lieu nhap vao
            if(userName.isBlank() || email.isBlank() || password.isBlank() || nameOfRestaurant.isBlank()){
                // thong bao nguoi dung dien chi tiet thong tin
                Toast.makeText(this, "Bạn hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }else{
                // dang ky tai khoan
                createAccount(email, password)
            }
        }
        // su ly khi da co tai khoan
        binding.alreadyHaveAnAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


        val locationList = arrayOf("HCM",  "Can Tho", "Ca Noi", "Quang Ngai", "Da Nang")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, locationList)

        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)


        //  thao tac su kien an hien  mat khau
        val editText = binding.password
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
    // ham dang ky tai khoan
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show()
                // luu thong tin nguoi dung vao database
                saveUserData()
                // dong thoi cho nguoi dung dang nhap
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Đăng kí tài khoản thất bại", Toast.LENGTH_SHORT).show()
                // log hien loi
                Log.d("Account","createAccount: Failure", task.exception)
            }
        }
    }

    private fun saveUserData() {
        email = binding.emailOrPhone.text.toString()
        password = binding.password.text.toString()
        nameOfRestaurant = binding.restaurantName.text.toString()
        userName = binding.nameAccount.text.toString()

        val user = UserModel(userName, nameOfRestaurant, email, password)
        val userId : String = FirebaseAuth.getInstance().currentUser!!.uid
        // luu thong tin nguoi dung vao Firebase Database
        database.child("user").child(userId).setValue(user)
    }
}