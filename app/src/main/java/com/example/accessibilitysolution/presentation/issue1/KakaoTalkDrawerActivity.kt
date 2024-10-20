package com.example.accessibilitysolution.presentation.issue1

import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityEvent
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
            // 전체 스크롤 범위
            val totalScrollRange = appBarLayout.totalScrollRange

            if (verticalOffset == 0) {
                // 완전히 펼쳐진 상태
                Log.d("CollapsingToolbar", "Expanded")
            } else if (Math.abs(verticalOffset) >= totalScrollRange) {
                // 완전히 접힌 상태
                Log.d("CollapsingToolbar", "Collapsed")

                // 다음 초점 지정
                binding.layout1.requestFocus()
                binding.layout1.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)

            } else {
                // 중간 상태
                Log.d("CollapsingToolbar", "Intermediate")
            }
        })

    }
}