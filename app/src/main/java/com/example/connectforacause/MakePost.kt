package com.example.connectforacause

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_make_post.*

class MakePost : AppCompatActivity() {

    private lateinit var reveive:Intent
    private val key_title="title"
    private val key_description="desc"
    private val key_url="image"
    private val key_editType="HSState"

    private val editOrg:Int=2
    private val addActivity:Int=3

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_make_post)

        reveive=intent
        val info=intent.extras
        val editType=info?.getInt(key_editType)
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
        info.putString(key_title, title)
        info.putString(key_description, description)
        info.putString(key_url, imgURL)
        info.putInt(key_editType, addActivity)

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
        info.putString(key_title, title)
        info.putString(key_description, description)
        info.putString(key_url, imgURL)
        info.putInt(key_editType, editOrg)

        val transmit=Intent(this, OrganisationHomeScreen::class.java)
        transmit.putExtras(info)
        startActivity(transmit)
    }
}