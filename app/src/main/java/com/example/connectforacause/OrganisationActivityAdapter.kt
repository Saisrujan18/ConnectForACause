package com.example.connectforacause

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_activity.view.*
import kotlinx.android.synthetic.main.item_description.view.*

class OrganisationActivityAdapter(private val Data: MutableList<Any>):RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private val ACTIVITY=0 //For Activity_Data
    private val DESC=1 //For Description_Data
    inner class ActivityViewHolder(ActivityView: View) : RecyclerView.ViewHolder(ActivityView)
    inner class DescriptionViewHolder(DescriptionView: View) : RecyclerView.ViewHolder(DescriptionView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        when(viewType)
        {
            ACTIVITY->{
                val v:View=inflater.inflate(R.layout.item_activity, parent, false)
                viewHolder=ActivityViewHolder(v)
            }
            DESC->{
                val v:View=inflater.inflate(R.layout.item_description, parent, false)
                viewHolder=DescriptionViewHolder(v)
            }
            else->{
                val v:View=inflater.inflate(R.layout.item_activity, parent, false)
                viewHolder=ActivityViewHolder(v)
            }
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        when(Data[position])
        {
            is Activity_Data->return ACTIVITY
            is Description_Data->return DESC
            else->return -1
        }
    }
    override fun getItemCount(): Int {
        return Data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType==ACTIVITY)
        {
            val activity=holder as ActivityViewHolder
            bindVH_Activity(activity, position)
        }
        else if(holder.itemViewType==DESC)
        {
            val description=holder as DescriptionViewHolder
            bindVH_Description(description, position)
        }
    }

    private fun bindVH_Activity(activity: OrganisationActivityAdapter.ActivityViewHolder, position: Int) {
        val data=Data[position] as Activity_Data
        activity.itemView.apply {
            tvActivityTitle.text=data.title
            tvActivityDescription.text=data.description
        }
    }
    private fun bindVH_Description(description: OrganisationActivityAdapter.DescriptionViewHolder, position: Int) {
        val data=Data[position] as Description_Data
        description.itemView.apply {
            tvNGODescription.text=data.description
        }
    }
}