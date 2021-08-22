package com.example.connectforacause

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickPublic(view: View) {
        val intent = Intent(this, OrganisationListActivity::class.java)
        startActivity(intent)
    }
    fun onClickNGOTest(view: View){
        val intent = Intent(this, OrganisationActivity::class.java)
        startActivity(intent)
    }
}