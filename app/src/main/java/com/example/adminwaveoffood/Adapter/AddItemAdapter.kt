package com.example.adminwaveoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.ItemMenuBinding

class AddItemAdapter(private val MenuItemName : ArrayList<String>, private val MenuItemPrice : ArrayList<String>, private val MenuItemNamRestaurant : ArrayList<String>, private val MenuItemImage : ArrayList<Int>) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuatities = IntArray(MenuItemName.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemName.size

    inner class AddItemViewHolder(private val biding : ItemMenuBinding) : RecyclerView.ViewHolder(biding.root){
        fun bind(position: Int){
         biding.apply {
                 var quantities = itemQuatities[position]
                 foodNameItem.text = MenuItemName[position]
                 nameRestaurantItem.text = MenuItemNamRestaurant[position]
                 priceItem.text = MenuItemPrice[position]
                 quantityFood.text = itemQuatities[position].toString()
                 imageFood.setImageResource(MenuItemImage[position])

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
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemNamRestaurant.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, MenuItemName.size)

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