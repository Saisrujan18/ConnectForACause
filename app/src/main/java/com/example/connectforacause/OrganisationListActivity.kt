package com.example.connectforacause

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_organisation_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrganisationListActivity : AppCompatActivity(), OrganisationClicked {

    private lateinit var receive:Intent
    private lateinit var adapter: OrganisationListAdapter
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
        val query = db.collection("Organisations")
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<OrganisationTileInfo>().setQuery(query,OrganisationTileInfo::class.java).build()

        adapter = OrganisationListAdapter(this,options = recyclerViewOptions)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

//    private fun fetchData(): Task<QuerySnapshot> {
//
//        val extraInfo=receive.extras
//        val login_type=extraInfo?.getInt(key_auth)
//        if(login_type==register)
//        {
//            val email=extraInfo?.getString(key_email)
//            val type=extraInfo?.getInt(key_type)
//
//            var entry : HashMap<String, Any> = HashMap<String, Any>()
//            entry.put(key_email, email?:"")
//            entry.put(key_type, type?:-1)
//            db.collection("Users").add(entry)
//            Toast.makeText(this, type.toString(), Toast.LENGTH_SHORT).show()
//        }
//        return db.collection("Organisations").get()
//    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
    private fun getOrganisationDetails(Oid: String): Task<DocumentSnapshot> {
        return db.collection("Organisations").document(Oid).get()
    }

    private fun getActivityDetails(Aid: String): Task<DocumentSnapshot>{
        return db.collection("Activities").document(Aid).get()
    }
    override fun onClick(Oid: String) {
        // intent Here.
        val organisationIntent = Intent(this@OrganisationListActivity, OrganisationActivity::class.java)
        var Data: ArrayList<ActivityInfo>
        var Description: String = "";
        Log.d(TAG, "DATA: SHIT ")
        val con = this
        CoroutineScope(Dispatchers.IO).launch {
            val organisationDoc =
                getOrganisationDetails(Oid).await().toObject(OrganisationTileInfo::class.java)
            Log.d(TAG, "actini ${organisationDoc?.Activities}")
            Description = organisationDoc?.Description!!

            Data = db.collection("Activities").whereIn(FieldPath.documentId(),organisationDoc.Activities.toList()).get().await().toObjects(ActivityInfo::class.java) as ArrayList<ActivityInfo>
            Log.d(TAG, "DATAm123: $Data")
            organisationIntent.putExtra("Description", Description)
            organisationIntent.putExtra("Activities", Data)
            startActivity(organisationIntent)
        }
        Toast.makeText(this, "clicked : $Oid", Toast.LENGTH_LONG).show()
    }
}