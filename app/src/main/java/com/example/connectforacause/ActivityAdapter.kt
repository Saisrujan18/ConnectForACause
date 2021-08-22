package com.example.connectforacause

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(val activities: List<Activity>):RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>()
{
    inner class ActivityViewHolder(ActivityView: View) : RecyclerView.ViewHolder(ActivityView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        //This is not Working
        //holder.itemView.apply{
    //      tvActivityTitle=activities.title
    //      tvActivityDescription=activities.description
    // }
    }


}