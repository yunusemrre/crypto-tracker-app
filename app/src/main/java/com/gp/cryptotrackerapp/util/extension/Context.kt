package com.gp.cryptotrackerapp.util.extension

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.databinding.CustomDialogCoinValBinding

fun Context.createAlertDialogYesNo(
    coin: String,
    onPositive: (max: String,min:String) -> Unit
): AlertDialog {
    val builder = MaterialAlertDialogBuilder(this)
    val binding: CustomDialogCoinValBinding = DataBindingUtil.inflate(
        LayoutInflater.from(this),
        R.layout.custom_dialog_coin_val,
        null,
        false
    )

    val dialog = builder.setView(binding.root).create()

    binding.mtvCustomDialogCoinInfo.text = coin
    binding.mtbCustomDialogApply.setOnClickListener{
        val max = binding.etCustomDialogMax.text.toString()
        val min = binding.etCustomDialogMin.text.toString()
        onPositive(max,min)

        dialog.dismiss()
    }
    binding.mtbCustomDialogCancel.setOnClickListener{
        dialog.dismiss()
    }

    return dialog
}