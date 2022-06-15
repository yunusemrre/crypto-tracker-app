package com.gp.cryptotrackerapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment
<B : ViewDataBinding,
        VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    lateinit var binding: B

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
//        binding.setVariable(BR.viewModel,this.viewModel)

        return binding.root
    }

    fun showProgressDialog() {
        (requireActivity() as BaseActivity<*, *>).showProgressDialog()
    }

    fun hideProgressDialog() {
        (requireActivity() as BaseActivity<*, *>).hideProgressDialog()
    }
}