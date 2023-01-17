package com.lok.dev.commonbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<Binding: ViewDataBinding> : BottomSheetDialogFragment() {

    private var _binding : Binding? = null
    protected val binding get() = _binding!!

    protected abstract fun createFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : Binding

    protected open fun initFragment() = Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = createFragmentBinding(inflater, container).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}