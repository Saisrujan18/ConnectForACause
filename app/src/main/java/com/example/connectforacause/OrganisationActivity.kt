package com.example.connectforacause

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OrganisationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation)

        //Testing

        var activityList= mutableListOf(
            Activity("XYZ", "SDG 1"),
            Activity("ABC", "SDG 2"),
            Activity("DEF", "SDG 3"),
            Activity("GHI", "SDG 4"),
            Activity("JKL", "SDG 5"),
            Activity("MNO", "SDG 6")
        )
        var adapter:ActivityAdapter=ActivityAdapter(activityList)
        
    }
}