package com.example.connectforacause

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_organisation.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class OrganisationActivity : AppCompatActivity() {
    private lateinit var receive: Intent
    private val db= Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_organisation)
        receive = intent
        val description = receive.extras?.getString("Description")
        val activities = receive.getSerializableExtra("Activities") as ArrayList<ActivityInfo>
        //Testing Assigning Dummy Data
        val Data: MutableList<Any> = mutableListOf()
        Data.add(Description_Data(description!!))
        for (activity in activities) {
            Data.add(Activity_Data(activity.Title, activity.Theme))
        }
//        Log.d(TAG, "Act : ${activities}")
        rvActivityList.layoutManager=LinearLayoutManager(this)
        val adapter = OrganisationActivityAdapter(Data)
        rvActivityList.adapter = adapter
    }
    private fun getData(Oid: String):MutableList<Any>
    {
        val Data: MutableList<Any> = mutableListOf()
        GlobalScope.launch {
            val organisationDoc = getOrganisationDetails(Oid).await().toObject(OrganisationTileInfo::class.java)
            Data.add(
                Description_Data(organisationDoc?.Description!!)
            )
            for ( activity in organisationDoc.Activities){
                GlobalScope.launch {
                    val activityDoc = getActivityDetails(activity).await().toObject(ActivityInfo::class.java)
                    Data.add(
                        Activity_Data(
                            activityDoc?.Title!!,
                            activityDoc.Theme
                        )
                    )
                }
            }
        }
        Log.d(TAG,"DATA ${Data}")
        return Data

    }

    private fun getOrganisationDetails(Oid: String): Task<DocumentSnapshot> {
        return db.collection("Organisations").document(Oid).get()
    }

    private fun getActivityDetails(Aid: String): Task<DocumentSnapshot>{
        return db.collection("Activities").document(Aid).get()
    }
}