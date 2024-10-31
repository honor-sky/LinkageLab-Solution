package com.example.accessibilitysolution.presentation.issue2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitysolution.R
import com.example.accessibilitysolution.databinding.ActivityBannerBinding
import com.example.accessibilitysolution.databinding.ActivityViewpagerBinding

class BannerActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityBannerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

    }

    fun initListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}