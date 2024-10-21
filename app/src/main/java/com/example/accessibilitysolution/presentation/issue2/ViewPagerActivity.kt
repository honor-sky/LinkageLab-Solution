package com.example.accessibilitysolution.presentation.issue2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.accessibilitysolution.R
import com.example.accessibilitysolution.databinding.ActivityViewpagerBinding

class ViewPagerActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityViewpagerBinding

    private val adapter = KakaotTBannerAdapter()
    val bannerImgList = mutableListOf(R.drawable.ic_home, R.drawable.ic_busstop)
    val bannerTitleList = mutableListOf("집으로한번에", "주변정류장")
    val bannermessageList = mutableListOf("위치 사용을 동의하세요.","위치 사용을동의하세요.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        initListener()

    }

    fun setupViewPager() {
        binding.listViewpager2.adapter = adapter
        adapter.setData(bannerImgList, bannerTitleList, bannermessageList)

        binding.listViewpager2.adapter = adapter
    }

    fun initListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }


}