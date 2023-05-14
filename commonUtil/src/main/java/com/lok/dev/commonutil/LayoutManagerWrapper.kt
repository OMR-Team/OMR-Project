package com.lok.dev.commonutil

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

// Inconsistency detected. Invalid view holder adapter positionViewHolder
// 리사이클러뷰 내 리스트 데이터를 반복적으로 갱신할때 발생하는 오류로 삼성 디바이스에서 발생하는 리사이클러뷰 오류
// 이를 해결하기 위해 wrapper 클래스 사용
class LayoutManagerWrapper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayoutManager(context, attrs, defStyleAttr, defStyleRes) {

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}