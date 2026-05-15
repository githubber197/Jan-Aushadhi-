package com.janaushadhi.finder.ui.stores

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.data.model.Store
import com.janaushadhi.finder.databinding.ItemStoreBinding

class StoreAdapter : ListAdapter<Store, StoreAdapter.StoreVH>(DIFF) {

    inner class StoreVH(val b: ItemStoreBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreVH {
        val b  = ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val vh = StoreVH(b)
        // Register click listeners once per ViewHolder (not on every bind)
        b.btnDirections.setOnClickListener {
            val pos = vh.bindingAdapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            val store = getItem(pos)
            val uri = Uri.parse("geo:${store.lat},${store.lng}?q=${Uri.encode(store.name)}")
            b.root.context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        b.btnCall.setOnClickListener {
            val pos = vh.bindingAdapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            val phone = getItem(pos).phone ?: return@setOnClickListener
            b.root.context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
        }
        return vh
    }

    override fun onBindViewHolder(holder: StoreVH, position: Int) {
        val store = getItem(position)
        holder.b.apply {
            tvStoreName.text    = store.name
            tvStoreAddress.text = store.fullAddress
            tvStorePhone.text   = store.phone ?: "N/A"
            tvStoreStatus.text  = store.statusLabel
            tvStoreStatus.setTextColor(
                ContextCompat.getColor(
                    root.context,
                    if (store.isOpen) R.color.green_open else R.color.red_closed
                )
            )
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(a: Store, b: Store) = a.id == b.id
            override fun areContentsTheSame(a: Store, b: Store) = a == b
        }
    }
}
