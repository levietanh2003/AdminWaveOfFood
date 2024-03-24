package com.example.adminwaveoffood.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.PendingOrderItemBinding

class PendingOrderAdapter(
    private val PendingOrderCustomerName : ArrayList<String>,
    private val PendingOrderQuantity : ArrayList<Int>,
    private val PendingOrderFoodImage : ArrayList<Int>,
    private val context : Context
    )
    :RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun getItemCount(): Int = PendingOrderCustomerName.size

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class PendingOrderViewHolder(private val binding: PendingOrderItemBinding) : RecyclerView.ViewHolder(binding.root){
        private var isAccepted = false
        fun bind(position: Int){
            binding.apply {
                pendingOrderNameCustomer.text = PendingOrderCustomerName[position]
                pendingOrderQuantity.text = PendingOrderQuantity[position].toString()
                pendingOrderImageFood.setImageResource(PendingOrderFoodImage[position])

                // thao tac voi don hang
                btnOrderedAccept.apply {
                    if(!isAccepted){
                        text = "Accept"
                    }else{
                        text = "Dispatch"

                    }
                    setOnClickListener {
                        if(!isAccepted){
                            isAccepted = true
                            text = "Dispatch"
                            showNotification("Order is accepted")
                        }else{
                            PendingOrderCustomerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showNotification("Order is dispatched")
                        }
                    }
                }
            }
        }

        // hien thong bao
        private fun showNotification(message : String){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}