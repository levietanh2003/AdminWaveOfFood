package com.example.adminwaveoffood.helper

import java.text.NumberFormat
import java.util.*

fun formatPriceVN(amount: Double): String {
    // ep kieu  tien te ve kieu viet nam
    val formatPriceVN = NumberFormat.getCurrencyInstance(Locale("vi","VN"))
    return  formatPriceVN.format(amount)

}