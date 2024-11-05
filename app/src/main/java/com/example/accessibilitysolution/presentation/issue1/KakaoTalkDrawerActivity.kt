package com.example.accessibilitysolution.presentation.issue1

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitysolution.databinding.ActivityTalkdrawerBinding
import com.google.android.material.appbar.AppBarLayout


class KakaoTalkDrawerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalkdrawerBinding

    var isCollaspNow = false // 영역 접힌 직후에 명시적 초점 이동 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTalkdrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initAccessibility()

    }


    fun initListener() {

        binding.scrollView.setSmoothScrollingEnabled(true)

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

                // 현재 초점이 접힌 화면에 있을 경우만 명시적으로 초점 이동
                if(!checkFocusContent(binding.contentLayout)) { // 스크롤 되는 영역(contentLayout)에 초점이 없으면 -> 강제 초점 이동
                    isCollaspNow = true
                    binding.layout1TextView1.requestFocus()
                    binding.layout1TextView1.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                }
            }
        })
    }

    fun initAccessibility() {

        binding.layout1TextView1.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            override fun performAccessibilityAction(host: View, action: Int, args: Bundle?): Boolean {
                return when (action) {
                    AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS -> {
                         /*
                         * 화면이 접힌 이후에 명시적으로 layout1Text로 초점 이동을 해준 경우와
                         * 접힘 상태에서 역탐색을 위해 다시 접힌 영역을 펴줘야 하는 경우 구분
                         */
                        if(!isCollaspNow) {
                            binding.appbar.setExpanded(true,true)   // 접힌 영역 펼치기
                            binding.scrollView.smoothScrollTo(0, 0)  // 스크롤 위로 최대한 올리기 (역탐색 비정상 수행 방지용)

                        } else {

                            isCollaspNow = false
                        }

                        super.performAccessibilityAction(host, action, args)
                    }
                        else -> super.performAccessibilityAction(host, action, args)
                }
            }
        })
    }

    // 접힌 영역을 제외한 영역에 초점이 있는지 확인
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