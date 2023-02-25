package com.lok.dev.commonbase

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lok.dev.commonmodel.state.AnimationState
import com.lok.dev.commonutil.animFragment
import com.lok.dev.commonutil.convertDpToPx

abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity() {

    protected val binding: Binding by lazy { createBinding() }

    protected abstract fun createBinding(): Binding

    protected open fun initActivity(savedInstanceState: Bundle?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.lifecycleOwner = this

        initActivity(savedInstanceState)
    }

    fun addFragment(
        @IdRes containerId: Int,
        fragment: Fragment?,
        animation: AnimationState = AnimationState.None,
        addBackStack: Boolean = false
    ) {
        requireNotNull(fragment)
        val transaction = supportFragmentManager.beginTransaction()
        animFragment(transaction, animation)
        transaction.add(containerId, fragment).apply {
            if (addBackStack) addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }

    fun replaceFragment(
        @IdRes containerId: Int,
        fragment: Fragment?,
        animation: AnimationState = AnimationState.None,
        addBackStack: Boolean = false
    ) {
        requireNotNull(fragment)
        val transaction = supportFragmentManager.beginTransaction()
        animFragment(transaction, animation)
        transaction.replace(containerId, fragment).apply {
            if (addBackStack) addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }

    fun removeFragment(fragment: Fragment?, animation: AnimationState = AnimationState.None) {
        if (fragment == null) return

        val transaction = supportFragmentManager.beginTransaction()
        animFragment(transaction, animation)
        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
    }

    val Number.dp: Int get() = convertDpToPx(this.toFloat()).toInt()
}