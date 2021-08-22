package com.example.connectforacause

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_organisation_list.*

class OrganisationListActivity : AppCompatActivity(), OrganisationClicked {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = fetchData()
        val adapter = OrganisationListAdapter(items, this)
        recyclerView.adapter = adapter
    }

    private fun fetchData(): ArrayList<String>{
        val list = ArrayList<String>()
        for (i in 0 until 100){
            list.add("Item $i")
        }
        return list
    }

    override fun onClick(item: String) {
        // intent Here.
        Toast.makeText(this, "clicked : $item", Toast.LENGTH_LONG).show()
    }
}