package com.gp.cryptotrackerapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.gp.cryptotrackerapp.util.dialog.CustomProgressDialog
import com.gp.cryptotrackerapp.util.enum.CurrencyEnum
import timber.log.Timber

abstract class BaseFragment
<B : ViewDataBinding,
        VM : BaseViewModel> : Fragment() {

    companion object {
        var globalCurrency: String = CurrencyEnum.USD.name
    }

    abstract val viewModel: VM
    lateinit var binding: B


    private val mLoadingDialog: CustomProgressDialog by lazy {
        CustomProgressDialog(requireContext())
    }

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

    fun navigateFragment(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    /**
     * Show progress dialog
     */
    fun showProgressDialog() {
        try {
            if (!mLoadingDialog.isShowing && !requireActivity().isFinishing) {
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