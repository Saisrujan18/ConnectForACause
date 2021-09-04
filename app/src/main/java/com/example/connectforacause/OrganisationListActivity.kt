package com.example.connectforacause

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_organisation_list.*

class OrganisationListActivity : AppCompatActivity(), OrganisationClicked {

    private lateinit var receive:Intent
    private val db=Firebase.firestore

    //receive ids same as transmit id
    private val key_email:String="email"
    private val key_type:String="type"
    private val key_auth:String="authtype"

    //user type
    private val ngo:Int=0
    private val volunteer:Int=1

    //login type
    private val register:Int=0
    private val signin:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_organisation_list)

        receive=intent
        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = fetchData()
        val adapter = OrganisationListAdapter(items, this)
        recyclerView.adapter = adapter
    }

    private fun fetchData(): ArrayList<OrganisationTileInfo>{
        val list = ArrayList<OrganisationTileInfo>()

        db.collection("Organisations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    val title:String = document.data["Title"].toString()
                    val description:String = document.data["Description"].toString()
                    val activities: List<String> = document.data.get("Activities") as List<String>
                    val photo:String = document.data["Photo"].toString()
//                    list.add(OrganisationTileInfo(Title = title, Description = description, Activities = activities, Photo = photo))
                    Log.d(TAG, "Title: ${photo}")
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "Error getting data.")
            }

        val extraInfo=receive.extras
        val login_type=extraInfo?.getInt(key_auth)
        if(login_type==register)
        {
            val email=extraInfo?.getString(key_email)
            val type=extraInfo?.getInt(key_type)

            var entry : HashMap<String, Any> = HashMap<String, Any>()
            entry.put(key_email, email?:"")
            entry.put(key_type, type?:-1)
            db.collection("Users").add(entry)
            Toast.makeText(this, type.toString(), Toast.LENGTH_SHORT).show()
        }
        return list
    }

    override fun onClick(item: String) {
        // intent Here.
        Toast.makeText(this, "clicked : $item", Toast.LENGTH_LONG).show()
    }
}