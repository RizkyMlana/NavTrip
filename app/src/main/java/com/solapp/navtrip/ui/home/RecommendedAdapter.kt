package com.solapp.navtrip.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solapp.navtrip.R
import com.solapp.navtrip.data.pref.RecommendedModel

class RecommendedAdapter(private val items: List<RecommendedModel>) :
    RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder>() {

    inner class RecommendedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_recommended)
        val title: TextView = itemView.findViewById(R.id.tv_recommended_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommended, parent, false)
        return RecommendedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.imageResId)
        holder.title.text = item.name
    }

    override fun getItemCount(): Int = items.size
}
