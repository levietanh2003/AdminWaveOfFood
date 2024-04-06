package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminwaveoffood.Model.TypeOfDish
import com.example.adminwaveoffood.databinding.ActivityTypeOfDishBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TypeOfDishActivity : AppCompatActivity() {

    private lateinit var typeOfDishName : String

    // firebase
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase

    private val binding: ActivityTypeOfDishBinding by lazy {
        ActivityTypeOfDishBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize firebase
        auth = FirebaseAuth.getInstance()
        // initialize database
        database = FirebaseDatabase.getInstance()

        // xu y su kien loai mon an
        binding.btnSaveType.setOnClickListener {
            // lay du lieu nhap vao
            typeOfDishName = binding.edtTypeName.text.toString()


            // kiem tra du lieu nhap vao
            if(typeOfDishName.isBlank()){
                // thong bao nguoi dung dien chi tiet thong tin
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }else{
                // dang ky mon an
                uploadData()
                Toast.makeText(this, "Thêm món thành công", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun uploadData() {
        val typeOfDishRef = database.getReference("TypeOfDish")

        // Tạo một đối tượng TypeOfDish với dữ liệu của loại món mới
        val newTypeOfDish = TypeOfDish(
            typeOfDishName = typeOfDishName
        )

        // Lưu dữ liệu của loại món vào Firebase Realtime Database với key tự động
        val newTypeOfDishItem = typeOfDishRef.push()
        newTypeOfDishItem.setValue(newTypeOfDish)
            .addOnSuccessListener {
                // Xử lý thành công
                Toast.makeText(this, "Thêm danh mục món ăn thành công", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Xử lý thất bại
                Toast.makeText(this, "Thêm danh mục món ăn thất bại", Toast.LENGTH_SHORT).show()
            }
    }

}