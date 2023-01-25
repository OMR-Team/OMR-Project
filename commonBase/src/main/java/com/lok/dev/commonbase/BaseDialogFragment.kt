package com.lok.dev.commonbase

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.lok.dev.commonBase.R

abstract class BaseDialogFragment<Binding : ViewDataBinding, Result> : DialogFragment() {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    private var dismissResult: Result? = null
    var result: ((result: Result?) -> Unit)? = null
    var cancel: () -> Unit = {}

    var canceledOnTouchOutside = true
    var fullScreen = false
    var bottomSlideAnimation = false

    protected abstract fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding

    protected open fun initData() = Unit
    protected open fun initDataBinding() = Unit
    protected open fun initDialogFragment(savedInstanceState: Bundle?) = Unit
    protected open fun onDialogBackPressed() { dismiss() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStyle()
    }

    protected open fun initStyle() {
        if (fullScreen)
            setStyle(STYLE_NO_TITLE, R.style.DialogFullScreen)
        else if (bottomSlideAnimation)
            setStyle(STYLE_NO_TITLE, R.style.BottomSlideDialog)
        else
            setStyle(STYLE_NO_TITLE, R.style.DialogCornerRadius)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                onDialogBackPressed()
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
        initDataBinding()
    }.root

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            isCancelable = canceledOnTouchOutside
            setCanceledOnTouchOutside(canceledOnTouchOutside)
        }
    }

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
        }
        super.onDismiss(dialog)
    }

    protected fun setResultOnDismiss(data: Result) {
        dismissResult = data
        dismiss()
    }

}