package com.example.connectforacause

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    //simplify ids to 0 and 1 for login type
    private val volunteer:Int=0
    private val ngo:Int=1

    //transmit ids
    private val key_email:String="email"
    private val key_type:String="type"

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        btnRegister.setOnClickListener{registerUser()}
        btnSignIn.setOnClickListener{signInUser()}
    }
    private fun registerUser()
    {
        val checkedID:Int=radioChoice.checkedRadioButtonId
        if(checkedID==-1)
        {
            Toast.makeText(this, "Please Select NGO/Volunteer", Toast.LENGTH_SHORT).show()
            return
        }
        val radioOption:Int=when(checkedID){
            radioNGO.id->0
            radioVolunteer.id->1
            else->-1
        }
        val email:String=etEmail.text.toString()
        val password:String=etPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty())
        {
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    auth.createUserWithEmailAndPassword(email, password)
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@LoginActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                    }
                    if(radioOption==volunteer) {
                        val transmit = Intent(this@LoginActivity, OrganisationListActivity::class.java)
                        transmit.putExtra(key_email, email)
                        transmit.putExtra(key_type, volunteer)
                        startActivity(transmit)
                    }
                }
                catch(e: Error)
                {
                    withContext(Dispatchers.Main)
                    {
                        when(e.message)
                        {
                            "auth/email-already-exists"-> Toast.makeText(this@LoginActivity, "User $email has already Registered. Please Sign In", Toast.LENGTH_SHORT).show()
                            "auth/invalid-email"-> Toast.makeText(this@LoginActivity, "The mail-id entered $email is invalid. Try Again", Toast.LENGTH_SHORT).show()
                            "auth/invalid-password"-> Toast.makeText(this@LoginActivity, "The password entered is invalid (Minimum 6 characters). Try Again", Toast.LENGTH_SHORT).show()
                            else-> Toast.makeText(this@LoginActivity, "Login Error. Please Try Again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    private fun signInUser()
    {
        val checkedID:Int=radioChoice.checkedRadioButtonId
        if(checkedID==-1)
        {
            Toast.makeText(this, "Please Select NGO/Volunteer", Toast.LENGTH_SHORT).show()
            return
        }
        val radioOption:Int=when(checkedID){
            radioNGO.id->0
            radioVolunteer.id->1
            else->-1
        }
        val email:String=etEmail.text.toString()
        val password:String=etPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty())
        {
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    auth.signInWithEmailAndPassword(email, password)
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@LoginActivity, "SignIn Successful", Toast.LENGTH_SHORT).show()
                    }
                    if(radioOption==volunteer) {
                        val transmit = Intent(this@LoginActivity, OrganisationListActivity::class.java)
                        transmit.putExtra(key_type, volunteer)
                        startActivity(transmit)
                    }
                }
                catch(e: Error)
                {
                    withContext(Dispatchers.Main)
                    {
                        when(e.message)
                        {
                            "auth/email-already-exists"-> Toast.makeText(this@LoginActivity, "User $email has already Registered. Please Sign In", Toast.LENGTH_SHORT).show()
                            "auth/invalid-email"-> Toast.makeText(this@LoginActivity, "The mail-id entered $email is invalid. Try Again", Toast.LENGTH_SHORT).show()
                            "auth/invalid-password"-> Toast.makeText(this@LoginActivity, "The password entered is invalid (Minimum 6 characters). Try Again", Toast.LENGTH_SHORT).show()
                            else-> Toast.makeText(this@LoginActivity, "Login Error. Please Try Again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}