package com.lok.dev.commonbase

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<Binding : ViewDataBinding, Result> : BottomSheetDialogFragment() {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    private var dismissResult: Result? = null
    var result: ((result: Result?) -> Unit)? = null
    var cancel: () -> Unit = {}

    protected abstract fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding

    protected open fun initData() = Unit
    protected open fun initDialogFragment(savedInstanceState: Bundle?) = Unit
    protected open fun onDialogBackPressed() { dismiss() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : BottomSheetDialog(requireContext(), theme) {
            override fun onBackPressed() {
                onDialogBackPressed()
                super.onBackPressed()
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createFragmentBinding(inflater, container).apply {
        lifecycleOwner = viewLifecycleOwner
        _binding = this
        initData()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialogFragment(savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (dismissResult != null) {
            result?.invoke(dismissResult)
        } else {
            cancel.invoke()
        }
        super.onDismiss(dialog)
    }

    protected fun resultAndDismiss(data: Result) {
        dismissResult = data
        dismiss()
    }

}