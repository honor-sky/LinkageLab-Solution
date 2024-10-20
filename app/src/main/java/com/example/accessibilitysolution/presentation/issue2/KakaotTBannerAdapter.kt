package com.example.accessibilitysolution.presentation.issue2

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

    private  var bannerDataList : MutableList<Int>? = null
    var prevPosition = 0


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

    override fun onBindViewHolder(holder: KakaotTBannerViewHolder, position: Int) {
        if (bannerDataList != null) {

            // holder가 관리하는 아이템(페이지)에 접근해 유형정보 바꿔줘야 함
            ViewCompat.setAccessibilityDelegate(holder.itemView, object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(v, info)
                    info.roleDescription = "버튼"
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
                            val pageInfoMessage = "${bannerDataList!!.size}개의 페이지 중 ${position + 1}번째 페이지"
                            val actionInfoMessage = "활성화 하려면 두번 클릭 하세요. 길게 누르려면 두번 클릭 후 유지 하세요."
                            holder.binding.bannerItemMain.announceForAccessibility(actionInfoMessage)
                            holder.binding.bannerItemMain.announceForAccessibility(pageInfoMessage)

                            prevPosition = position
                        } else {
                            //val pageInfoMessage = "${bannerDataList!!.size}개의 페이지 중 ${position + 1}번째 페이지"
                            val actionInfoMessage = "활성화 하려면 두번 클릭 하세요. 길게 누르려면 두번 클릭 후 유지 하세요."
                            //holder.binding.bannerItemMain.announceForAccessibility(pageInfoMessage)
                            holder.binding.bannerItemMain.announceForAccessibility(actionInfoMessage)

                            prevPosition = position
                        }
                        }
                    return handled
                    }
            })

            holder.bind(bannerDataList!![position])
        }
    }

    override fun getItemCount() = bannerDataList?.size ?: 0



}