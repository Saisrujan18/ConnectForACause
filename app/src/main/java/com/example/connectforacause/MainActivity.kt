package com.example.connectforacause

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickPublic(view: View)
    {
//      // testing..
        db.collection("Organisations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result)
                {
                    Toast.makeText(this, "$document.id", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }

        val intent = Intent(this, OrganisationListActivity::class.java)
        startActivity(intent)
    }
    fun onClickNGOTest(view: View){
        val intent = Intent(this, OrganisationActivity::class.java)
        startActivity(intent)
    }
}