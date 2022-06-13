package com.gp.cryptotrackerapp.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>
    (@LayoutRes private val layoutId: Int) : AppCompatActivity(layoutId) {

    lateinit var binding: B
    abstract val viewModel: VM

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this@BaseActivity
//        binding.setVariable(BR.viewModel,this.viewModel)

        init(savedInstanceState)
    }
}