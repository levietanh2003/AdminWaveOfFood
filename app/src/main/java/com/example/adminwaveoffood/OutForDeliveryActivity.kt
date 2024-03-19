package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.Adapter.DeliveryAdapter
import com.example.adminwaveoffood.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {

    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // su lien vao btnBack
        binding.btnBack.setOnClickListener {
            finish()
        }

        var customerName = arrayListOf("Thuan Thanh", "My Dung", "Nam Duong")
        var moneyStatus = arrayListOf("Đã nhận", "Chưa nhận","Chưa giải quyết")

        var adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.deliveryRecyclerView.adapter = adapter
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}