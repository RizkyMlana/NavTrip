package com.solapp.navtrip.ui.destination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solapp.navtrip.R
import com.solapp.navtrip.data.pref.DestinationModel

class DestinationAdapter(private val list: List<DestinationModel>) :
    RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
        val imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destination, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.name
        holder.tvCategory.text = item.category
        holder.tvDescription.text = item.description
        holder.tvDistance.text = item.distance
        holder.imgThumbnail.setImageResource(item.imageResId)
    }
}
