package com.example.connectforacause

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_organisation.*

class OrganisationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation)

        //Testing Assigning Dummy Data
        var activityList=getData()

        rvActivityList.layoutManager=LinearLayoutManager(this)
        var adapter:ActivityAdapter=ActivityAdapter(activityList)
        rvActivityList.adapter=adapter
    }
    private fun getData():MutableList<Activity_Data>
    {
        return mutableListOf(
            Activity_Data("XYZ", "SDG 1"),
            Activity_Data("ABC", "SDG 2"),
            Activity_Data("DEF", "SDG 3"),
            Activity_Data("GHI", "SDG 4"),
            Activity_Data("JKL", "SDG 5"),
            Activity_Data("MNO", "SDG 6")
        )
    }
}