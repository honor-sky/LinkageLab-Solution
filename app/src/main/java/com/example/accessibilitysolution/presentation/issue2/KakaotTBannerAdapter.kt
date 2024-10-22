package com.example.accessibilitysolution.presentation.issue2

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.accessibilitysolution.databinding.ItemBannerBinding


class KakaotTBannerAdapter(): RecyclerView.Adapter<KakaotTBannerAdapter.KakaotTBannerViewHolder>() {

    private var bannerImgDataList : MutableList<Int>? = null
    private var bannerTitleDataList : MutableList<String>? = null
    private var bannerMessageDataList : MutableList<String>? = null

    var prevPosition = 0


    fun setData(bannerImgDataList : MutableList<Int>, bannerTitleDataList : MutableList<String>, bannerMessageDataList : MutableList<String>) {
        bannerImgDataList.let {
            this.bannerImgDataList = bannerImgDataList
        }

        bannerTitleDataList.let {
            this.bannerTitleDataList = bannerTitleDataList
        }

        bannerMessageDataList.let {
            this.bannerMessageDataList = bannerMessageDataList
        }
    }

    inner class KakaotTBannerViewHolder (val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(idx: Int) {
            binding.bannerIcon.background =
                bannerImgDataList?.get(idx)?.let { binding.root.context.getDrawable(it) };
            binding.bannerTitle.text = bannerTitleDataList?.get(idx) ?: ""
            binding.bannerMessage.text = bannerMessageDataList?.get(idx) ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaotTBannerViewHolder {
        return KakaotTBannerViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: KakaotTBannerViewHolder, @SuppressLint("RecyclerView") position: Int) {

            // holder가 관리하는 아이템(페이지)에 접근해 유형정보 바꿔줘야 함
            ViewCompat.setAccessibilityDelegate(holder.itemView, object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(v, info)
                    info.roleDescription = "버튼" //"." 으로 하면 음성 출력은 안 되긴 함
                }

                override fun performAccessibilityAction(
                    host: View,
                    action: Int,
                    args: Bundle?
                ): Boolean {
                    val handled = super.performAccessibilityAction(host, action, args)  // 기본 처리 먼저
                    if(action ==  AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS ) {
                        // 원래 정보 출력 뒤 커스텀 메시지를 안내
                        if(prevPosition == position) {
                            val pageInfoMessage = "총 ${bannerImgDataList!!.size}페이지 중 ${position + 1}페이지"
                            val actionInfoMessage = "활성화 하려면 두번 클릭 하세요. 길게 누르려면 두번 클릭 후 유지 하세요."
                            holder.binding.bannerItemMain.announceForAccessibility(actionInfoMessage)
                            holder.binding.bannerItemMain.announceForAccessibility(pageInfoMessage)

                            prevPosition = position
                        } else {
                            val actionInfoMessage = "활성화 하려면 두번 클릭 하세요. 길게 누르려면 두번 클릭 후 유지 하세요."
                            holder.binding.bannerItemMain.announceForAccessibility(actionInfoMessage)

                            prevPosition = position
                        }
                        }
                    return handled
                    }
            })

            holder.bind(position)

    }

    override fun getItemCount() = bannerImgDataList?.size ?: 0



}