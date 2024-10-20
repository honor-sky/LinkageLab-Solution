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
    val bannerImgList = mutableListOf(R.drawable.img_viewpager_busstop, R.drawable.img_viewpager_home)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()


    }

    fun setupViewPager() {
        binding.listViewpager2.adapter = adapter
        adapter.setData(bannerImgList)

        binding.listViewpager2.adapter = adapter
    }

    fun setPage() {

    }

    fun moveToNextBanner() {

    }

}