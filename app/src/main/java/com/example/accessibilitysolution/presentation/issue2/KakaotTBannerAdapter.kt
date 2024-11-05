package com.example.accessibilitysolution.presentation.issue2

import android.R
import android.R.attr.button
import android.annotation.SuppressLint
import android.media.session.PlaybackState.CustomAction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.accessibilitysolution.databinding.ItemBannerBinding


class KakaotTBannerAdapter(val isClickEvent : Boolean, val isGestureInfo : Boolean,
                           val isButton : Boolean, val clickAction : () -> Unit): RecyclerView.Adapter<KakaotTBannerAdapter.KakaotTBannerViewHolder>() {

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

            if(isGestureInfo) {
            binding.bannerItemMain.setOnClickListener {
                if(isClickEvent) {
                    clickAction()
                }
            }
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaotTBannerViewHolder {
        return KakaotTBannerViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: KakaotTBannerViewHolder, position: Int) {

         /* holder가 관리하는 아이템(페이지)에 접근해 유형정보 바꿔줘야 함 */
            ViewCompat.setAccessibilityDelegate(holder.itemView, object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(v, info)
                    if(isButton) {
                        info.roleDescription = "페이지 버튼"
                    }
                }

                override fun performAccessibilityAction(
                    host: View,
                    action: Int,
                    args: Bundle?
                ): Boolean {
                    // 원래 정보 출력 뒤 커스텀 메시지를 안내
                    val handled = super.performAccessibilityAction(host, action, args)

                    // 초점 접근
                    if(action == AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS) {
                        // 최초 접근일때만
                        if(prevPosition == position) {
                            val pageInfoMessage = "총 ${bannerImgDataList!!.size}페이지 중 ${position + 1}페이지"
                            holder.binding.bannerItemMain.announceForAccessibility(pageInfoMessage)

                            prevPosition = position

                        } else {
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