package com.example.accessibilitysolution.presentation.issue1

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.accessibilitysolution.databinding.ItemBannerBinding


class KakaotTBannerAdapter(): RecyclerView.Adapter<KakaotTBannerAdapter.KakaotTBannerViewHolder>() {

    private  var bannerDataList : MutableList<Int>? = null


    fun setData(bannerDataList : MutableList<Int>) {
        bannerDataList.let {
            this.bannerDataList = bannerDataList
        }
    }

    inner class KakaotTBannerViewHolder (val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaotTBannerViewHolder {
        return KakaotTBannerViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: KakaotTBannerViewHolder, position: Int) {
        if (bannerDataList != null) {

            //holder.binding.bannerItemMain.contentDescription =  "${bannerDataList!!.size}개의 페이지 중 ${position + 1}번째 페이지"

            //holder.binding.bannerItemMain.roleDescription = "Button"
            ViewCompat.setAccessibilityDelegate(holder.itemView, object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(v, info)

                    // roleDescription 설정 - 필요에 따라 변경
                    info.roleDescription = "Button"

                    // 접근성 설명 (contentDescription) 설정
                }
            })
            holder.bind(bannerDataList!![position])
        }
    }

    override fun getItemCount() = bannerDataList?.size ?: 0



}