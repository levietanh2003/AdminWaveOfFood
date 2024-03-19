package com.example.adminwaveoffood.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.DeliveryItemBinding

class DeliveryAdapter(private val DeliveryCustomerName : ArrayList<String>, private val DeliveryMoneyStatus : ArrayList<String>): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = DeliveryCustomerName.size

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                customerName.text = DeliveryCustomerName[position]
                moneyStatus.text = DeliveryMoneyStatus[position]
                // set colo trang thai cho mat hang
                var colorMap = mapOf("Đã nhận" to Color.GREEN, "Không nhận" to Color.RED, "Đá xuất kho" to Color.BLUE)
                moneyStatus.setTextColor(colorMap[DeliveryMoneyStatus[position]]?:Color.BLACK)
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[DeliveryMoneyStatus[position]]?:Color.BLACK)
            }
        }

    }
}