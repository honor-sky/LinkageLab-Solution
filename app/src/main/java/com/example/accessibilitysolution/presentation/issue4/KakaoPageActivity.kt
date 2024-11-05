package com.example.accessibilitysolution.presentation.issue4

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitysolution.R
import com.example.accessibilitysolution.databinding.ActivityNobelBinding


class KakaoPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNobelBinding
    private lateinit var viewmodel : KakaoPageViewModel

    val REQUEST_CODE_WRITE_SETTINGS = 1000

    var brightnessProgress : Int = 40


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNobelBinding.inflate(layoutInflater)
        viewmodel = KakaoPageViewModel()
        setContentView(binding.root)

        initListener()
        initAccessibility()

        // 슬라이더 초기화
        binding.brightSlider.progress = brightnessProgress

        // 초기 버튼 색 설정
        if(binding.pageBtn.isSelected) {
            binding.pageBtn.setTextColor(Color.BLACK)
            binding.scrollBtn.setTextColor(Color.GRAY)
        } else {
            binding.pageBtn.setTextColor(Color.GRAY)
            binding.scrollBtn.setTextColor(Color.BLACK)
        }

    }


    fun initListener() {

        binding.systemBrightBtn.setOnClickListener {
            viewmodel.setSystemBrighter()
        }

        // 슬라이더 리스너
        binding.brightSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(this@KakaoPageActivity)) {
                        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                        intent.setData(Uri.parse("package:$packageName"))
                        startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS)
                    } else {
                        // 권한이 이미 허용된 경우 밝기 설정 함수 호출
                        brightnessProgress = progress
                        setScreenBrightness(brightnessProgress)
                    }
                } else {
                    // Android 6.0 미만에서는 직접적으로 설정 가능
                    brightnessProgress = progress
                    setScreenBrightness(brightnessProgress)
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // 추가 동작이 필요할 경우
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // 추가 동작이 필요할 경우
            }
        })

        // 버튼 클릭 리스너
        binding.systemBrightBtn.setOnClickListener {

            if(binding.brightSlider.isEnabled) {
                // 슬라이더 비활성화
                binding.brightSlider.isEnabled = false

                // 기기 화면 밝기 설정 따르기
                val deviceBrightness = getCurrentScreenBrightness()

            } else {
                binding.brightSlider.isEnabled = true
                binding.brightSlider.progress = brightnessProgress
                setScreenBrightness(brightnessProgress)
            }

        }

        binding.nextPageGroup.setOnCheckedChangeListener { group, checkedId ->

            // 선택된 버튼의 텍스트 색상을 검정색으로 변경
            when (checkedId) {
                R.id.pageBtn -> {
                    binding.pageBtn.setTextColor(Color.BLACK)
                    binding.scrollBtn.setTextColor(Color.GRAY)
                }

                R.id.scrollBtn ->  {
                    binding.pageBtn.setTextColor(Color.GRAY)
                    binding.scrollBtn.setTextColor(Color.BLACK)
                }

            }
        }


    }

    private fun getCurrentScreenBrightness(): Int {
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 255)
    }

    private fun setScreenBrightness(brightnessValue: Int) {
        // 밝기 값 설정
        if (brightnessValue in 0..255) {
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightnessValue)
            // 즉시 반영
            val layoutParams = window.attributes
            layoutParams.screenBrightness = brightnessValue / 255f
            window.attributes = layoutParams

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Settings.System.canWrite(this)) {
                // 권한이 허용된 경우
                setScreenBrightness(brightnessProgress) // 밝기 설정 함수 호출
            } else {
                // 권한이 거부된 경우
                Toast.makeText(this, "WRITE_SETTINGS 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
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