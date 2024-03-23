package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.Adapter.FeedBackAdapter
import com.example.adminwaveoffood.databinding.ActivityAdminFeedBackMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdminFeedBackMainActivity : AppCompatActivity() {

    private val binding: ActivityAdminFeedBackMainBinding by lazy {
        ActivityAdminFeedBackMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // su ly su kien btnBack
        binding.btnBack.setOnClickListener {
            finish()
        }

        var customerName = arrayListOf("Thuan Thanh", "My Dung", "Nam Duong")
        var contentFeedBack = arrayListOf("Khong thich", "Khong thich", "Khong thich")
        var timeTamp = arrayListOf("12/12/2022", "12/12/2022", "12/12/2022")

        var adapter = FeedBackAdapter(ArrayList(customerName), ArrayList(contentFeedBack), ArrayList(timeTamp))
        binding.feedBackRecyclerView.adapter = adapter
        binding.feedBackRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}