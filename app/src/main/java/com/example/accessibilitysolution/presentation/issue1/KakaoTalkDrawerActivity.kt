package com.example.accessibilitysolution.presentation.issue1

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.AccessibilityDelegate
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.example.accessibilitysolution.databinding.ActivityTalkdrawerBinding
import com.google.android.material.appbar.AppBarLayout
import java.util.Objects

class KakaoTalkDrawerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalkdrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTalkdrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    fun initListener() {

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange

            if (verticalOffset == 0) {
                // 완전히 펼쳐진 상태
                binding.drawerTtile.visibility = View.GONE

            } else if (Math.abs(verticalOffset) >= totalScrollRange) {
                // 완전히 접힌 상태
                binding.drawerTtile.visibility = View.VISIBLE

                // 다음 초점 지정
                // 현재 초점이 content 영역에 있다면 초점 이동 안 함
                if(!checkFocusContent(binding.contentLayout)) {
                    // collaspToolbar 영역에 있다면 layout1으로 가도록 지정
                    binding.layout1Text.requestFocus()
                    binding.layout1Text.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                }
            }
        })

        binding.layout1Text.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            override fun performAccessibilityAction(host: View, action: Int, args: Bundle?): Boolean {
                return when (action) {
                    AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS -> {
                        // AppBar 펼치기
                        binding.appbar.setExpanded(true,true)
                        // 스크롤 위로 최대한 올리기
                        binding.scrollView.smoothScrollTo(0, 0)

                        super.performAccessibilityAction(host, action, args)
                    }
                    else -> super.performAccessibilityAction(host, action, args)
                }
            }
        })
    }

    fun checkFocusContent(view: ViewGroup) : Boolean {
        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)

            if(child is ViewGroup) {
                val viwegroup = view.getChildAt(i) as ViewGroup
                if(checkFocusContent(viwegroup)){
                    return true
                }
            }
            if (child.isAccessibilityFocused) {
                // 현재 포커스를 가진 자식 뷰가 있음
                return true
            }
        }
        return false
    }
}