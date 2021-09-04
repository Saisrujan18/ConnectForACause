package com.example.connectforacause

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrganisationListAdapter(private val items: ArrayList<OrganisationTileInfo>, private val listener: OrganisationClicked): RecyclerView.Adapter<OrganisationListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisationListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.organisation_tile, parent, false)
        val viewHolder = OrganisationListViewHolder(view)
        view.setOnClickListener{
            listener.onClick(items[viewHolder.adapterPosition].Title)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: OrganisationListViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.Title
        holder.activityCount.text = currentItem.Activities?.size as String
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class OrganisationListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val image: ImageView = itemView.findViewById(R.id.oimage)
    val title: TextView = itemView.findViewById(R.id.otitle)
    val notifications: TextView = itemView.findViewById(R.id.notifications)
    val activityCount: TextView = itemView.findViewById(R.id.activitycount)
}

interface OrganisationClicked{
    fun onClick(item: String)
}