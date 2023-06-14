package com.lok.dev.commonbase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.lok.dev.commonutil.LifecycleOwnerWrapper
import com.lok.dev.commonutil.convertDpToPx

abstract class BaseFragment<Binding : ViewDataBinding> : Fragment(), LifecycleOwnerWrapper {

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

    override fun initLifeCycleOwner(): LifecycleOwner = viewLifecycleOwner

    protected abstract fun createFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : Binding

    protected open fun initFragment() = Unit

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected open fun onFragmentBackPressed(): Unit = Unit

    val Number.dp: Int get() = requireContext().convertDpToPx(this.toFloat()).toInt()

}