package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.Adapter.MenuItemAdapter
import com.example.adminwaveoffood.Model.AllMenu
import com.example.adminwaveoffood.databinding.ActivityAllItemBinding
import com.google.firebase.database.*

class AllItemActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database : FirebaseDatabase
    private var menuItems : ArrayList<AllMenu> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference

        // viet ham truy xuat Menu
        retrieveMenuItem()

        //val adapterMenu = MenuItemAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice),ArrayList(menuItemNamRestaurant), ArrayList(menuItemImage))
//        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        //binding.menuRecyclerView.adapter = adapterMenu

        // xu ly su kien btnBack
        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        // fetch data from data base
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                // clear existing data before populating
                menuItems.clear()

                // loop for through each food item
                for(foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = MenuItemAdapter(this@AllItemActivity, menuItems,databaseReference)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
    }
}