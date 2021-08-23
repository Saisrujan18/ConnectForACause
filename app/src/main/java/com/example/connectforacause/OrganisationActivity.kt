package com.example.connectforacause

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_organisation.*
import kotlinx.android.synthetic.main.item_description.*

class OrganisationActivity : AppCompatActivity() {

    private var isScrollEnabled:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_organisation)

        //Testing Assigning Dummy Data
        var activityList=getData()

        rvActivityList.layoutManager=LinearLayoutManager(this)
        var adapter=Activity_DescriptionAdapter(activityList)
        rvActivityList.adapter=adapter
    }
    private fun getData():MutableList<Any>
    {
        println(R.string.description)
        return mutableListOf(
            Description_Data("Hello this is Sample Data for NGO\n"+getString(R.string.description)),
            Activity_Data("XYZ", "SDG 1"),
            Activity_Data("ABC", "SDG 2"),
            Activity_Data("DEF", "SDG 3"),
            Activity_Data("GHI", "SDG 4"),
            Activity_Data("JKL", "SDG 5"),
            Activity_Data("MNO", "SDG 6")
        )
    }
}