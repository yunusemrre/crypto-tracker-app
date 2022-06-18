package com.gp.cryptotrackerapp.util.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long.toDateString(): String{
    return Date(this).formatDate()
}

@SuppressLint("SimpleDateFormat")
fun Date.formatDate(): String{
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.formatDateSimple(): String{
    val format = SimpleDateFormat("MM.dd HH:mm")
    return format.format(this)
}

fun String.toDoubleDate(): Double{
    return try {
        var date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(this)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date as Date
        calendar.add(Calendar.HOUR,3)
        date = calendar.time
        val dateLong = date?.time as Long
        dateLong.toDouble()
    }catch (e: Exception){
        0.0
    }
}