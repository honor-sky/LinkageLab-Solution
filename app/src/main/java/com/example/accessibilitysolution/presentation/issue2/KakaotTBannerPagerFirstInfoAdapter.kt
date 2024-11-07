package com.example.accessibilitysolution.presentation.issue2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.accessibilitysolution.R
import com.example.accessibilitysolution.databinding.ItemBannerBinding

class KakaotTBannerPagerFirstInfoAdapter (): RecyclerView.Adapter<KakaotTBannerPagerFirstInfoAdapter.KakaotTBannerOnlyPageInfoViewHolder>() {

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

    inner class KakaotTBannerOnlyPageInfoViewHolder (val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(idx: Int) {
            binding.bannerIcon.background =
                bannerImgDataList?.get(idx)?.let { binding.root.context.getDrawable(it) };
            binding.bannerTitle.text = bannerTitleDataList?.get(idx) ?: ""
            binding.bannerMessage.text = bannerMessageDataList?.get(idx) ?: ""

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaotTBannerOnlyPageInfoViewHolder {
        return KakaotTBannerOnlyPageInfoViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: KakaotTBannerOnlyPageInfoViewHolder, position: Int) {

        /* holder가 관리하는 아이템(페이지)에 접근해 유형정보 바꿔줘야 함 */
        ViewCompat.setAccessibilityDelegate(holder.itemView, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(v, info)
                info.roleDescription = "페이지"
            }

            override fun performAccessibilityAction(
                host: View,
                action: Int,
                args: Bundle?
            ): Boolean {
                // 원래 정보 출력 뒤 커스텀 메시지를 안내

                val pagerInfo = "총 ${bannerImgDataList!!.size}페이지 중 ${position + 1}페이지"
                val messageInfo = "페이지, ${bannerTitleDataList!!.get(position)}, ${bannerMessageDataList!!.get(position)}"
                val actionInfo = "활성화 하려면 두번 탭하세요"

                if(prevPosition == position) {
                    //holder.itemView.requestFocus()
                    holder.itemView.announceForAccessibility(pagerInfo)
                    holder.itemView.announceForAccessibility(messageInfo)
                    holder.itemView.announceForAccessibility(actionInfo)
                }

                prevPosition = position

                return false

            }
        })

        holder.bind(position)

    }

    override fun getItemCount() = bannerImgDataList?.size ?: 0


}