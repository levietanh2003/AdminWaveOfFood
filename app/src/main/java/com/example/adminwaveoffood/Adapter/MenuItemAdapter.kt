package com.example.adminwaveoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwaveoffood.Model.AllMenu
import com.example.adminwaveoffood.databinding.ItemMenuBinding
import com.example.adminwaveoffood.helper.formatPriceVN
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context : Context,
    private val menuList : ArrayList<AllMenu>,
    databaseReference: DatabaseReference

) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuatities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size

    inner class AddItemViewHolder(private val biding : ItemMenuBinding) : RecyclerView.ViewHolder(biding.root){
        fun bind(position: Int){
         biding.apply {
                 var quantities = itemQuatities[position]
                 val menuItem = menuList[position]
                 val uriString = menuItem.foodImage
                 val uri = Uri.parse(uriString)
                 foodNameItem.text = menuItem.foodName
                 typeOfDish.text = menuItem.typeOfDishId
                 priceItem.text = menuItem.foodPrice?.let { formatPriceVN(it.toDouble()) } ?: "N/A"
                 Glide.with(context).load(uri).into(imageFood)
                 quantityFood.text = itemQuatities[position].toString()

                 // thao tac giam so luong
                 btnMinus.setOnClickListener {
                     decreaseQuatity(position)
                 }
                 // thao tac tnag so luong
                 btnPlus.setOnClickListener {
                     increaseQuatity(position)
                 }
                 // thao tac xoa san pham
                 btnTrash.setOnClickListener {
                     deleteQuatity(position)
                 }
             }
        }

        private fun deleteQuatity(position: Int) {
            // thao tac cap nhat vao realtime database
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)

        }

        private fun increaseQuatity(position: Int) {
            if(itemQuatities[position] < 10){
                itemQuatities[position]++
                biding.quantityFood.text = itemQuatities[position].toString()

            }
        }

        private fun decreaseQuatity(position: Int) {
            if(itemQuatities[position] > 1){
                itemQuatities[position]--
                biding.quantityFood.text = itemQuatities[position].toString()
            }
        }
    }
}