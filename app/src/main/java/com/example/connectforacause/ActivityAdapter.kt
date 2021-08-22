package com.example.connectforacause

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_activity.view.*

class ActivityAdapter(val activityData: List<Activity_Data>):RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>()
{
    inner class ActivityViewHolder(ActivityView: View) : RecyclerView.ViewHolder(ActivityView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return activityData.size
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.itemView.apply{
            tvActivityTitle.text=activityData[position].title
            tvActivityDescription.text=activityData[position].description
        }
    }


}