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
import com.lok.dev.commonBase.R

abstract class BaseDialogFragment<Binding : ViewDataBinding, Result> : DialogFragment() {

    protected val TAG get() = this::class.java.simpleName

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    protected var dismissResult: Result? = null
    var success: ((result: Result?) -> Unit)? = null
    var cancel: () -> Unit = {}

    var windowWidth = ViewGroup.LayoutParams.MATCH_PARENT
    var windowHeight = ViewGroup.LayoutParams.MATCH_PARENT
    var bottomSlideAnimation = false
    open var cancelOnTouchOutside = true
    var dimBehind = false
    var showKeyboardInput = false

    protected abstract fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                onDialogBackPressed()
            }
        }
        return dialog
    }

    protected open fun onDialogBackPressed() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (bottomSlideAnimation) setStyle(STYLE_NO_TITLE, R.style.BottomSlideDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createFragmentBinding(inflater, container).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    protected open fun initFragment() = Unit

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            setCanceledOnTouchOutside(cancelOnTouchOutside)
            window?.apply {
                val gravity = if (bottomSlideAnimation) Gravity.BOTTOM else Gravity.CENTER
                setGravity(gravity)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(windowWidth, windowHeight)
                if (dimBehind) {
                    addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    setDimAmount(0.5f)
                } else {
                    clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isDestroyed && !manager.isStateSaved) super.show(manager, tag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        if(dismissResult != null) {
            success?.invoke(dismissResult)
        }else {
            cancel.invoke()
        }
        super.onDismiss(dialog)
    }

    protected fun dismiss(data: Result) {
        dismissResult = data
        dismiss()
    }

}