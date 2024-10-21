package com.example.accessibilitysolution.presentation.issue1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitysolution.databinding.ActivityTalkdrawerBinding
import com.google.android.material.appbar.AppBarLayout

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
                // 현재 초점이 이미 layout1 에 존재하면 이어서 탐색


                // collaspToolbar 영역에 있다면 layout1으로 가도록 지정
                binding.layout1Text.requestFocus()
                binding.layout1Text.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)

            } else {
                // 중간 상태

            }
        })

    }

    fun checkFocusContent(view: ViewGroup) : Boolean {
        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)
            if (child.isFocused) {
                // 현재 포커스를 가진 자식 뷰가 있음
                Log.d("FocusedView", "Focused child: ${child.id}")
                return true
            }
        }
        return false
    }


}