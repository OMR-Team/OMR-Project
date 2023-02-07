package com.lok.dev.commonbase

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.lok.dev.commonBase.R

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

    private fun animFragment(transaction: FragmentTransaction, animation: AnimationState) {
        when (animation) {
            AnimationState.None -> Unit
            AnimationState.Left -> {
                transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
            }
            AnimationState.Right -> {
                transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out)
            }
            AnimationState.Up -> {
                transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out)
            }
            AnimationState.Down -> {
                transaction.setCustomAnimations(R.anim.push_down_in, R.anim.push_down_out)
            }
            AnimationState.Fade -> {
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            }
        }
    }

    fun removeFragment(fragment: Fragment?, animation: AnimationState = AnimationState.None) {
        if (fragment == null) return

        val transaction = supportFragmentManager.beginTransaction()
        animFragment(transaction, animation)
        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
    }

    sealed interface AnimationState {
        object None : AnimationState
        object Left : AnimationState
        object Right : AnimationState
        object Up : AnimationState
        object Down : AnimationState
        object Fade : AnimationState
    }
}