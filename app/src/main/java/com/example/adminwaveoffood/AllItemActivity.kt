package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.Adapter.AddItemAdapter
import com.example.adminwaveoffood.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val menuFoodName = listOf("Bánh Hot Dog", "SandWich", "momo", "item", "item", "item")
        val menuItemPrice = listOf("100.000", "200.000", "300.000", "400.000", "500.000", "600.000")
        val menuItemNamRestaurant = listOf("Nam Restaurant", "Nam Restaurant", "Nam Restaurant", "Nam Restaurant", "Nam Restaurant", "Nam Restaurant")
        val menuItemImage = listOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4, R.drawable.menu5, R.drawable.menu1)

        val adapterMenu = AddItemAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice),ArrayList(menuItemNamRestaurant), ArrayList(menuItemImage))
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapterMenu

        // xu ly su kien btnBack
        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}