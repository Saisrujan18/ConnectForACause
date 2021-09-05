package com.example.connectforacause

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_make_post.*

class MakePost : AppCompatActivity() {

    private lateinit var reveive:Intent

    private val editOrg:Int=2
    private val addActivity:Int=3

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_post)
        this.supportActionBar!!.hide()

        reveive=intent
        val info=intent.extras
        val editType=info?.getInt(getString(R.string.key_editType))
        if(editType==editOrg)
        {
            btnPost.setOnClickListener{submitChanges()}
            tvPostTitle.setText("Enter Organisation Name ")
            tvPostDescription.setText("Enter Organisation Desciption ")
            tvPostImage.setText("Enter valid Image URL")
        }
        else if(editType==addActivity)
        {
            btnRegister.setOnClickListener{submitPost()}
            tvPostTitle.setText("Enter a One Liner which describes the Problem/Activity")
            tvPostDescription.setText("Problem/Activity Description")
            tvPostImage.setText("Enter valid Image URL")
        }
    }

    private fun submitPost()
    {
        val title:String=etPostTitle.text.toString()
        val description:String=etPostDescription.text.toString()
        val imgURL:String=etPostImage.text.toString()

        val info=Bundle()
        info.putString(getString(R.string.key_title), title)
        info.putString(getString(R.string.key_description), description)
        info.putString(getString(R.string.key_url), imgURL)
        info.putInt(getString(R.string.key_editType), addActivity)

        val transmit=Intent(this, OrganisationHomeScreen::class.java)
        transmit.putExtras(info)
        startActivity(transmit)
    }

    private fun submitChanges()
    {
        val title:String=etPostTitle.text.toString()
        val description:String=etPostDescription.text.toString()
        val imgURL:String=etPostImage.text.toString()

        val info=Bundle()
        info.putString(getString(R.string.key_title), title)
        info.putString(getString(R.string.key_description), description)
        info.putString(getString(R.string.key_url), imgURL)
        info.putInt(getString(R.string.key_editType), editOrg)

        val transmit=Intent(this, OrganisationHomeScreen::class.java)
        transmit.putExtras(info)
        startActivity(transmit)
    }
}