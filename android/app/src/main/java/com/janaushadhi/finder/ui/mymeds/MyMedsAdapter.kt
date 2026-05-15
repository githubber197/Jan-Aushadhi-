package com.janaushadhi.finder.ui.mymeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.data.model.MyMed
import com.janaushadhi.finder.databinding.ItemMymedBinding

class MyMedsAdapter(
    private val onDelete: (MyMed) -> Unit
) : ListAdapter<MyMed, MyMedsAdapter.MyMedVH>(DIFF) {

    inner class MyMedVH(val b: ItemMymedBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMedVH {
        val b  = ItemMymedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val vh = MyMedVH(b)
        // Register delete listener once per ViewHolder, not on every bind
        b.btnDelete.setOnClickListener {
            val pos = vh.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onDelete(getItem(pos))
        }
        return vh
    }

    override fun onBindViewHolder(holder: MyMedVH, position: Int) {
        val med = getItem(position)
        holder.b.apply {
            tvMedName.text      = "💊 ${med.name}"
            tvRefillStatus.text = med.refillStatusLabel()
            tvQuantity.text     = "📦 ${med.quantityPerMonth} tablets/month"
            tvRefillStatus.setTextColor(
                ContextCompat.getColor(
                    root.context,
                    if (med.isUrgent()) R.color.orange_urgent else R.color.teal_primary
                )
            )
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<MyMed>() {
            override fun areItemsTheSame(a: MyMed, b: MyMed) = a.id == b.id
            override fun areContentsTheSame(a: MyMed, b: MyMed) = a == b
        }
    }
}
