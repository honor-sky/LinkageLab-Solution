package com.example.accessibilitysolution.presentation.issue1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
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
        setAccessibility()

    }

    fun setAccessibility() {



        ViewCompat.setAccessibilityDelegate(binding.textBtn,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(v, info)
                    info.roleDescription = "Button"
                }
            })

        ViewCompat.setAccessibilityDelegate(binding.listViewpager2,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(v, info)
                    info.roleDescription = "Button"
                }
            })

        ViewCompat.setAccessibilityDelegate(binding.buttonBtn,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(v, info)
                    info.roleDescription = "Text"
                }
            })


    }




    fun setupViewPager() {
        binding.listViewpager2.adapter = adapter

        adapter.setData(bannerImgList)

       /* val adapter1 = object : Ada() {

            override fun getCount(): Int {
                return bannerImgList.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                binding.listViewpager.addView(bannerImgList[position])
                return viewList[position]
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                binding.listViewpager.removeView(`object` as View)
            }

        }

        binding.listViewpager.adapter1 = adapter*/


    }

}