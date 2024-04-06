package com.example.adminwaveoffood

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.Model.AllMenu
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding
import com.example.adminwaveoffood.helper.formatPriceVN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    // food item Details
    private lateinit var foodName : String
    private lateinit var foodPrice : String
    private lateinit var foodDescription : String
    private lateinit var foodIngredient : String
    private lateinit var typeOfDishRef: DatabaseReference
    private var foodImage : Uri? = null

    // firebase
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private var selectedTypeOfDishId: String? = null

    val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize firebase
        auth = FirebaseAuth.getInstance()
        // initialize database
        database = FirebaseDatabase.getInstance()

        // Reference to "TypeOfDish" node
        typeOfDishRef = database.getReference("TypeOfDish")

        // Set up Spinners
        setUpSpinners()

        // xu y su kien nhap ten mon an
        binding.btnAddMenu.setOnClickListener {
            // lay du lieu nhap vao
            foodName = binding.nameFoodAddMenu.text.toString()
            foodPrice = binding.priceFoodAddMenu.text.toString()
            foodDescription = binding.contentDescriptionAddMenu.text.toString()
            foodIngredient = binding.ingredientFoodAddMenu.text.toString()

            // kiểm tra xem đã chọn danh mục chưa
            if (selectedTypeOfDishId.isNullOrEmpty()) {
                Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // kiem tra du lieu nhap vao
            if(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank()){
                // thong bao nguoi dung dien chi tiet thong tin
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }else{
                // dang ky mon an
                uploadData()
                Toast.makeText(this, "Thêm món thành công", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.spinnerTyOfDish.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Lấy typeOfDishId tương ứng với sự lựa chọn từ DataSnapshot
                val typeOfDishId = (parent?.getItemAtPosition(position) as? String)
                selectedTypeOfDishId = typeOfDishId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Xử lý khi không có mục nào được chọn
            }
        }


        // Xử lý sự kiện khi người dùng chọn ảnh
        binding.txtSelectImage.setOnClickListener {
            pickImage.launch("image/*")
        }
        // xu ly su kien back button
        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun setUpSpinners() {
        val typeOfDishList = mutableListOf<String>()
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, typeOfDishList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTyOfDish.adapter = adapter

        // Load type of dish data from Firebase
        typeOfDishRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val typeOfDishName = ds.child("typeOfDishName").getValue(String::class.java)
                    typeOfDishName?.let {
                        typeOfDishList.add(it)
                    }
                }
                // Log to check the content of typeOfDishList
                Log.d("TypeOfDishList", typeOfDishList.toString())
                adapter.notifyDataSetChanged()
                Log.d("Load danh mục", "Dữ liệu đã được tải thành công từ Firebase")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@AddItemActivity, "Load danh mục thất bại", Toast.LENGTH_SHORT).show()
                Log.e("Lỗi load danh mục", error.message)
            }
        })
    }

    private fun uploadData() {
        // lấy tham chiếu đến nút "menu" trong cơ sở dữ liệu
        val menuRef = database.getReference("menu")
        // tao khoa chinh
        val menuItemKey = menuRef.push().key

        if(foodImage != null){
            // tao upload task
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${menuItemKey}.jpg")
            val upLoadTask = imageRef.putFile(foodImage!!)

            upLoadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // create a new menu item
                    val menuItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUri.toString(),
                        typeOfDishId = selectedTypeOfDishId

                    )
                    menuItemKey?.let {
                        key ->
                        menuRef.child(key).setValue(menuItem).addOnSuccessListener {
                            Toast.makeText(this, "Thêm món mới thành công", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Thao tác thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
            }.addOnFailureListener {
                    Toast.makeText(this, "Cập nhật ảnh thất bại", Toast.LENGTH_SHORT).show()
                }
        }
        }else{
            Toast.makeText(this, "Hãy chọn ảnh", Toast.LENGTH_SHORT).show()
        }
    }    // Đăng ký hoạt động nhận kết quả từ việc chọn ảnh
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri ->
            // Hiển thị ảnh đã chọn lên ImageView
            binding.selectedImage.setImageURI(imageUri)
            foodImage = imageUri
        }
    }
}
