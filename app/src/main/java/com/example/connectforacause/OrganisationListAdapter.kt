package com.example.connectforacause

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class OrganisationListAdapter(private val listener: OrganisationClicked,
                              options: FirestoreRecyclerOptions<OrganisationTileInfo>
): FirestoreRecyclerAdapter<OrganisationTileInfo, OrganisationListViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisationListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.organisation_tile, parent, false)
        val viewHolder = OrganisationListViewHolder(view)
        view.setOnClickListener{
            listener.onClick(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }


    override fun onBindViewHolder(
        holder: OrganisationListViewHolder,
        position: Int,
        model: OrganisationTileInfo
    ) {
        holder.title.text = model.Title
        holder.activityCount.text = "Active: ${model.Activities?.size.toString()}"
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