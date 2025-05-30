package com.solapp.navtrip.ui.hotspots

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solapp.navtrip.R

data class Hotspot(
    val name: String,
    val category: String,
    val description: String,
    val distance: String,
    val imageRes: Int
)

class HotspotAdapter(private val list: List<Hotspot>) :
    RecyclerView.Adapter<HotspotAdapter.HotspotViewHolder>() {

    class HotspotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPlace: ImageView = itemView.findViewById(R.id.imgPlace)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotspotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotspot, parent, false)
        return HotspotViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotspotViewHolder, position: Int) {
        val item = list[position]
        holder.tvTitle.text = item.name
        holder.tvCategory.text = item.category
        holder.tvDescription.text = item.description
        holder.tvDistance.text = item.distance
        holder.imgPlace.setImageResource(item.imageRes)
    }

    override fun getItemCount(): Int = list.size
}
