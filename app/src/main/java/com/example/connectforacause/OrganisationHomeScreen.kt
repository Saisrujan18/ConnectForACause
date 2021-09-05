package com.example.connectforacause

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_organisation_home_screen.*

class OrganisationHomeScreen : AppCompatActivity() {

    private val db= Firebase.firestore

    private val register:Int=0
    private val signin:Int=1
    private val editOrg:Int=2
    private val addActivity:Int=3

    private lateinit var receive: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_organisation_home_screen)
        this.supportActionBar!!.hide()

        receive=intent
        updateData()

        btnEditOrg.setOnClickListener{editDescription()}
        btnAddPost.setOnClickListener{addPost()}

        //Testing Assigning Dummy Data
        var activityList=getData()

        rvOrgHS.layoutManager= LinearLayoutManager(this)
        var adapter=OrganisationActivityAdapter(activityList)
        rvOrgHS.adapter=adapter
    }
    private fun getData() : MutableList<Any> {
        //Load data from DataBase
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
    private fun updateData()
    {
        val info=receive.extras
        if(info!=null)
        {
            val entryType=info.getInt(getString(R.string.key_HSState))
            if(entryType==register)
            {
                val email=info.getString(getString(R.string.key_email))
                val type=info.getInt(getString(R.string.key_type))
                var entry : HashMap<String, Any> = HashMap<String, Any>()
                entry.put(getString(R.string.key_email), email?:"")
                db.collection("Organisations").add(entry)
                entry.put(getString(R.string.key_type), type?:-1)
                db.collection("Users").add(entry)
            }
            if(entryType==editOrg)
            {
                val title=info.getString(getString(R.string.key_title))
                val description=info.getString(getString(R.string.key_description))
                val imgURL=info.getString(getString(R.string.key_url))
                var entry : HashMap<String, Any> = HashMap<String, Any>()
                entry.put(getString(R.string.key_title), title?:"")
                entry.put(getString(R.string.key_description), description?:"")
                entry.put(getString(R.string.key_url), imgURL?:"")
                //Add it to the same Organisation idk How
            }
            if(entryType==addActivity)
            {
                //Add it to the Activity
            }
        }
    }
    private fun editDescription()
    {
        Toast.makeText(this, "Evde Thanne deyy", Toast.LENGTH_LONG)
        val transmit=Intent(this, MakePost::class.java)
        val info=Bundle()
        info.putInt(getString(R.string.key_HSState), editOrg)
        transmit.putExtras(info)
        startActivity(transmit)
    }
    private fun addPost()
    {
        val transmit=Intent(this, MakePost::class.java)
        val info=Bundle()
        info.putInt(getString(R.string.key_HSState), addActivity)
        transmit.putExtras(info)
        startActivity(transmit)
    }
}