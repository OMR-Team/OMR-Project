package com.lok.dev.commonbase

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lok.dev.commonBase.R
import com.lok.dev.commonutil.LifecycleOwnerWrapper
import com.lok.dev.commonutil.convertDpToPx

abstract class BaseDialogFragment<Binding : ViewDataBinding, Result> : DialogFragment(), LifecycleOwnerWrapper {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    private var dismissResult: Result? = null
    var result: ((result: Result?) -> Unit)? = null
    var cancel: () -> Unit = {}

    var windowWidth = ViewGroup.LayoutParams.MATCH_PARENT
    var windowHeight = ViewGroup.LayoutParams.MATCH_PARENT

    var canceledOnTouchOutside = true
    var fullScreen = false
    var bottomSlideAnimation = false
    var dimBehind = false

    protected abstract fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding

    protected open fun initData() = Unit
    protected open fun initDataBinding() = Unit
    protected open fun initDialogFragment(savedInstanceState: Bundle?) = Unit
    protected open fun onDialogBackPressed() { dismiss() }

    override fun initLifeCycleOwner(): LifecycleOwner = viewLifecycleOwner

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
            setCanceledOnTouchOutside(canceledOnTouchOutside)
            isCancelable = canceledOnTouchOutside
            window?.apply {
                val gravity = if (bottomSlideAnimation) Gravity.BOTTOM else Gravity.CENTER
                window?.setGravity(gravity)
                setLayout(windowWidth, windowHeight)

                if(dimBehind) {
                    addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    setDimAmount(0.5f)
                }else clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
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
        }else {
            cancel.invoke()
        }
        super.onDismiss(dialog)
    }

    protected fun setResultOnDismiss(data: Result) {
        dismissResult = data
        dismiss()
    }

    protected fun ConstraintLayout.backgroundTouchDismiss() {
        this.setOnClickListener { onDialogBackPressed() }
    }

    val Number.dp: Int get() = requireContext().convertDpToPx(this.toFloat()).toInt()

}