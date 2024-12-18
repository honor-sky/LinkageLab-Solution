package com.example.accessibilitysolution.presentation.issue2

import android.content.Intent
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
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.accessibilitysolution.R
import com.example.accessibilitysolution.databinding.ActivityViewpagerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewPagerActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityViewpagerBinding


    private val adapterWithEvent = KakaotTBannerAdapter(true, true, false, { clickAction() })
    private val adapterWithoutEvent = KakaotTBannerAdapter(false, false, false, { clickAction() })
    private val adapterButton = KakaotTBannerAdapter(false, true, true, { clickAction() })
    private val adapterPagerFirstNoFocus = KakaotTBannerPagerFirstInfoAdapter() //KakaotTBannerPagerFirstInfoAdapter()
    private val adapterPagerFirstFocus = KakaotTBannerPagerFirstInfoFocusAdapter()

    val bannerImgList = mutableListOf(R.drawable.ic_home, R.drawable.ic_busstop)
    val bannerTitleList = mutableListOf("집으로한번에", "주변정류장")
    val bannermessageList = mutableListOf("위치 사용을 동의하세요.","위치 사용을 동의하세요.")

    var prevPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        initListener()

    }

    fun setupViewPager() {

        // 유형 정보(페이저) + 콘텐츠 정보 + 페이지 개수 정보(총 몇페이지~) + 클릭 이벤트 O
        adapterWithEvent.setData(bannerImgList, bannerTitleList, bannermessageList)
        binding.viewpagerWithEvent.adapter = adapterWithEvent

        // 유형 정보(페이저) +  콘텐츠 정보 + 페이지 개수 정보(총 몇페이지~) + 클릭 이벤트 X +
        adapterWithoutEvent.setData(bannerImgList, bannerTitleList, bannermessageList)
        binding.viewpagerWithoutEvent.adapter = adapterWithoutEvent

        // 유형 정보(버튼) + 콘텐츠 정보 + 페이지 개수 정보 + 클릭 이벤트 X
        adapterButton.setData(bannerImgList, bannerTitleList, bannermessageList)
        binding.viewpagerButton.adapter = adapterButton


        // 페이지 개수 정보 + 유형 정보(페이저) + 콘텐츠 정보 + 클릭 이벤트 X (초점 x)
        adapterPagerFirstNoFocus.setData(bannerImgList, bannerTitleList, bannermessageList)
        binding.viewpagerPagerInfoFirstNoFocus.adapter = adapterPagerFirstNoFocus

        binding.viewpagerPagerInfoFirstNoFocus.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
            override fun onPageSelected(position: Int) {

                // 페이지 변경 시 커스텀 접근성 메시지 출력
                val messageInfo = "페이지, ${bannerTitleList!!.get(position)}, ${bannermessageList!!.get(position)}"
                val actionInfo = "활성화 하려면 두번 탭하세요"

                CoroutineScope(Dispatchers.IO).launch {
                    delay(2500)
                    binding.viewpagerPagerInfoFirstNoFocus.announceForAccessibility(messageInfo)
                    binding.viewpagerPagerInfoFirstNoFocus.announceForAccessibility(actionInfo)
                }

            }

        })

        // 페이지 개수 정보 + 유형 정보(페이저) + 콘텐츠 정보 + 클릭 이벤트 X
        adapterPagerFirstFocus.setData(bannerImgList, bannerTitleList, bannermessageList)
        binding.viewpagerPagerInfoFirstFocus.adapter = adapterPagerFirstFocus


    }

    fun initListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    fun clickAction() {
        startActivity(Intent(this, BannerActivity::class.java))
    }
}