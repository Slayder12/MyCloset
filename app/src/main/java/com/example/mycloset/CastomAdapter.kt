package com.example.mycloset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val items: MutableList<Item>) :
RecyclerView.Adapter<CustomAdapter.ItemViewHolder>()
{
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemNameTV: TextView = itemView.findViewById(R.id.itemNameTV)
        val descriptionItemTV: TextView = itemView.findViewById(R.id.descriptionItemTV)
        val imageIV: ImageView = itemView.findViewById(R.id.imageIV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemNameTV.text = item.name
        holder.descriptionItemTV.text = item.description
        holder.imageIV.setImageResource(item.image)
    }
}