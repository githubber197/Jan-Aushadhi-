package com.janaushadhi.finder.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.databinding.ItemMedicineBinding

class MedicineAdapter(
    private val onClick: (Medicine) -> Unit
) : ListAdapter<Medicine, MedicineAdapter.MedVH>(DIFF) {

    inner class MedVH(val b: ItemMedicineBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedVH {
        val b = ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val vh = MedVH(b)
        // Set click listener once per ViewHolder (not on every bind) — avoids creating a new
        // lambda object each time RecyclerView recycles and re-binds the same ViewHolder
        b.root.setOnClickListener {
            val pos = vh.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onClick(getItem(pos))
        }
        return vh
    }

    override fun onBindViewHolder(holder: MedVH, position: Int) {
        val med = getItem(position)
        holder.b.apply {
            tvMedIcon.text    = med.icon
            tvBrandName.text  = med.brand
            tvSaltName.text   = med.salt
            tvBrandPrice.text = "₹${med.brand_price.toInt()}"
            tvGenPrice.text   = "₹${med.generic_price.toInt()}"
            tvSaveBadge.text  = "Save ${med.savingsPercent}%"
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Medicine>() {
            override fun areItemsTheSame(a: Medicine, b: Medicine) = a.id == b.id
            override fun areContentsTheSame(a: Medicine, b: Medicine) = a == b
        }
    }
}
