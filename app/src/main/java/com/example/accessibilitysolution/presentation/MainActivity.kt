package com.example.accessibilitysolution.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitysolution.databinding.ActivityMainBinding
import com.example.accessibilitysolution.presentation.issue1.KakaoTalkDrawerActivity
import com.example.accessibilitysolution.presentation.issue2.ViewPagerActivity
import com.example.accessibilitysolution.presentation.issue4.KakaoPageActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    fun initListener() {
        binding.viewpagerBtn.setOnClickListener {
            startActivity(Intent(this, ViewPagerActivity::class.java))
        }

        binding.talkDrawerFocusBtn.setOnClickListener {
            startActivity(Intent(this, KakaoTalkDrawerActivity::class.java))
        }

        binding.kakaoPageNovelBtn.setOnClickListener {
            startActivity(Intent(this, KakaoPageActivity::class.java))
        }


    }

}