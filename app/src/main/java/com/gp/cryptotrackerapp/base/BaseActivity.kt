package com.gp.cryptotrackerapp.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.util.dialog.CustomProgressDialog
import timber.log.Timber

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>
    (@LayoutRes private val layoutId: Int) : AppCompatActivity(layoutId) {

    lateinit var binding: B
    abstract val viewModel: VM

    private val mLoadingDialog: CustomProgressDialog by lazy {
        CustomProgressDialog(this)
    }

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this@BaseActivity
//        binding.setVariable(BR.viewModel,this.viewModel)

        init(savedInstanceState)
    }

    /**
     * Show progress dialog
     */
    fun showProgressDialog() {
        try {
            if (!mLoadingDialog.isShowing && !isFinishing) {
                mLoadingDialog.show()
                Timber.d("Progress dialog is showing")
            }
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }

    /**
     * Hide progress dialog
     */
    fun hideProgressDialog() {
        try {
            if (mLoadingDialog.isShowing) {
                mLoadingDialog.dismiss()
                Timber.d("Progress dialog is dismissed")
            }
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }
}