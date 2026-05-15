package com.janaushadhi.finder.ui.learn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.databinding.ItemLearnCardBinding

class LearnAdapter(
    private val cards: List<Triple<String, String, String>>
) : RecyclerView.Adapter<LearnAdapter.LearnVH>() {

    private val expanded = mutableSetOf<Int>()

    inner class LearnVH(val b: ItemLearnCardBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnVH {
        val b = ItemLearnCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LearnVH(b)
    }

    override fun onBindViewHolder(holder: LearnVH, position: Int) {
        val (emoji, title, body) = cards[position]
        holder.b.apply {
            tvEmoji.text     = emoji
            tvTitle.text     = title
            tvBody.text      = body
            tvBody.visibility = if (position in expanded) View.VISIBLE else View.GONE
            ivChevron.rotation = if (position in expanded) 180f else 0f

            root.setOnClickListener {
                if (position in expanded) expanded.remove(position) else expanded.add(position)
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = cards.size
}
