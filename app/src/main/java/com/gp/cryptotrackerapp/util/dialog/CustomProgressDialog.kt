package com.gp.cryptotrackerapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.databinding.CustomProgressDialogBinding

class CustomProgressDialog(private var _context: Context) : Dialog(_context, R.style.LoadingDialogStyle) {

    lateinit var binding: CustomProgressDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(_context),
            R.layout.custom_progress_dialog,
            null,
            false
        )
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            binding.pbCustomProgress.indeterminateDrawable.colorFilter = BlendModeColorFilter(
                ContextCompat.getColor(_context, R.color.white),
                BlendMode.SRC_ATOP
            )
        else binding.pbCustomProgress.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(
                _context,
                android.R.color.white
            ), PorterDuff.Mode.SRC_ATOP
        )
    }
}