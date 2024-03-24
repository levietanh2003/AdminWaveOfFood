package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.Adapter.PendingOrderAdapter
import com.example.adminwaveoffood.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {

    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // su kien back
        binding.btnBack.setOnClickListener{
            finish()
        }
        // do du lieu local
        var pendingOrderCustomerName = arrayListOf("Customer 1","Customer 2","Customer 3","Customer 4","Customer 5")
        var pendingOrderQuantity = arrayListOf(1,2,3,4,5)
        var pendingOrderFoodImage = arrayListOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,R.drawable.menu5)

        var adapter = PendingOrderAdapter(ArrayList(pendingOrderCustomerName),
            ArrayList(pendingOrderQuantity), ArrayList(pendingOrderFoodImage),
        this)

        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}