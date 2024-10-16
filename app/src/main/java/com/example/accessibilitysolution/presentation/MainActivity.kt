package com.example.accessibilitysolution.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitysolution.databinding.ActivityMainBinding
import com.example.accessibilitysolution.presentation.issue1.ViewPagerActivity

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


    }

}