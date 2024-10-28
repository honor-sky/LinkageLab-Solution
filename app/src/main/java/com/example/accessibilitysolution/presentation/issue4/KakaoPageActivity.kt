package com.example.accessibilitysolution.presentation.issue4

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitysolution.databinding.ActivityNobelBinding


class KakaoPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNobelBinding
    private lateinit var viewmodel : KakaoPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNobelBinding.inflate(layoutInflater)
        viewmodel = KakaoPageViewModel()
        setContentView(binding.root)


        val window = window
        val layoutParams = window.attributes
        layoutParams.screenBrightness = (50).toFloat()
        window.attributes = layoutParams

        initListener()
        initObserver()
        initAccessibility()
    }

    fun initListener() {

        binding.systemBrightBtn.setOnClickListener {
            viewmodel.setSystemBrighter()
        }


        binding.brightSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val window = window
                val layoutParams = window.attributes
                layoutParams.screenBrightness = (p1).toFloat()
                window.attributes = layoutParams
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })

    }


    fun initObserver(){
        viewmodel.isSelectedSystemBrighter.observe(this) {
            if(it) {
                // 시스템 설정 따름
                setScreenBrighter()
                binding.brightSlider.isEnabled = false
            } else {
                binding.brightSlider.isEnabled = true
            }
        }

    }


    fun setScreenBrighter() {
        val window = window
        val layoutParams = window.attributes
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        window.attributes = layoutParams
    }

    fun initAccessibility() {



        /*
        binding.brightSlider.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun performAccessibilityAction(host: View, action: Int, args: Bundle?): Boolean {
                return when (action) {
                    AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS -> {
                        if(viewmodel.isSelectedSystemBrighter.value!!) {
                            binding.brightSlider.stateDescription = "선택안됨, ${binding.brightSlider.progress}"
                        } else {
                            binding.brightSlider.stateDescription = "선택됨, ${binding.brightSlider.progress}"
                        }

                        //binding.brightSlider.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                        super.performAccessibilityAction(host, action, args)
                    }
                    else -> super.performAccessibilityAction(host, action, args)
                }
            }
        })

         */

    }
}