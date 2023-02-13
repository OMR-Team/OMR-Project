package com.lok.dev.commonbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<Binding : ViewDataBinding> : Fragment() {

    private var _binding : Binding? = null
    protected val binding get() = _binding!!

    protected open val enableBackPressed: Boolean = false

    private val backPressedCallback by lazy {
        object : OnBackPressedCallback(enableBackPressed) {
            override fun handleOnBackPressed() {
                onFragmentBackPressed()
            }
        }
    }

    protected abstract fun createFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : Binding

    protected open fun initFragment() = Unit

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected open fun onFragmentBackPressed(): Unit = Unit

}