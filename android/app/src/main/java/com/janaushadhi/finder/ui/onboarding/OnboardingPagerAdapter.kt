package com.janaushadhi.finder.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.databinding.ItemOnboardingSlideBinding

class OnboardingPagerAdapter(
    private val slides: List<Triple<String, String, String>>
) : RecyclerView.Adapter<OnboardingPagerAdapter.SlideVH>() {

    inner class SlideVH(val binding: ItemOnboardingSlideBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideVH {
        val binding = ItemOnboardingSlideBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SlideVH(binding)
    }

    override fun onBindViewHolder(holder: SlideVH, position: Int) {
        val (emoji, title, body) = slides[position]
        holder.binding.slideEmoji.text  = emoji
        holder.binding.slideTitle.text  = title
        holder.binding.slideBody.text   = body
    }

    override fun getItemCount() = slides.size
}
