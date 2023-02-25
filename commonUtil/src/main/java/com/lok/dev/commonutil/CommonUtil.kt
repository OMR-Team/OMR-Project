package com.lok.dev.commonutil

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.lok.dev.commonUtil.R
import com.lok.dev.commonmodel.state.AnimationState

fun animFragment(transaction: FragmentTransaction, animation: AnimationState) {
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

fun Context.getChangeTextStyle(
    str: String,
    changeStr: String,
    @ColorRes colorRes: Int,
    @FontRes fontRes: Int = 0
): SpannableStringBuilder = SpannableStringBuilder(str).also { ssb ->
    val strIdx = str.indexOf(changeStr)
    val start = strIdx.takeIf { it > -1 } ?: 0
    val end = strIdx.takeIf { it > -1 }.let { start + changeStr.length }
    val flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

    ssb.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, colorRes)), start, end, flag)
    fontRes.takeIf { it > 0 }?.run { ssb.setSpan(StyleSpan(Typeface.BOLD), start, end, flag) }
}