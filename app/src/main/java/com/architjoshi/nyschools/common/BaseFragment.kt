package com.architjoshi.nyschools.common

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

/**
 * Base Fragment class to provide common functionality useful across different fragments
 */
abstract class BaseFragment<T : ViewModel> : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<T>

    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().enableOnBackPressed(true)
    }

    protected fun showLoadingDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Loading")
        loadingDialog = builder.create().apply {
            setCanceledOnTouchOutside(false)
        }.also { it.show() }
    }

    protected fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }
}