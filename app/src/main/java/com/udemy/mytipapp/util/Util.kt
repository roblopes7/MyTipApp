package com.udemy.mytipapp.util

fun calculateTotalTip(totalBill: Double, tipPercentage: Int): Double {
    return if(totalBill > 1
        && totalBill.toString().isNotEmpty())
        (totalBill * tipPercentage)  / 100 else 0.0
}