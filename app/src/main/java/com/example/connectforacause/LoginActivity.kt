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
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    //simplify ids to 0 and 1 for user type and login type
    private val ngo:Int=0
    private val volunteer:Int=1

    private val register:Int=0
    private val signin:Int=1

    private var success:Boolean=false

    //transmit ids
    private val key_email:String="email"
    private val key_type:String="type"
    private val key_auth:String="authtype"

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(checkSuccess())
//            signIn(1, (auth.currentUser?.email)?:"")

        setContentView(R.layout.activity_auth)
        auth = FirebaseAuth.getInstance()
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
            radioNGO.id->ngo
            radioVolunteer.id->volunteer
            else->-1
        }
        val email:String=etEmail.text.toString()
        val password:String=etPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty())
        {
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    auth.createUserWithEmailAndPassword(email, password).await()

                    withContext(Dispatchers.Main)
                    {
                        success=checkSuccess()
                        Toast.makeText(this@LoginActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                    }
                }
                catch(e: Error)
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
//                        when(e.message?:"")
//                        {
//                            "auth/email-already-exists"-> Toast.makeText(this@LoginActivity, "User $email has already Registered. Please Sign In", Toast.LENGTH_SHORT).show()
//                            "auth/invalid-email"-> Toast.makeText(this@LoginActivity, "The mail-id entered $email is invalid. Try Again", Toast.LENGTH_SHORT).show()
//                            "auth/invalid-password"-> Toast.makeText(this@LoginActivity, "The password entered is invalid (Minimum 6 characters). Try Again", Toast.LENGTH_SHORT).show()
//                            else-> Toast.makeText(this@LoginActivity, "Login Error. Please Try Again", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
                if(success)
                    register(radioOption, email)
            }
        }
    }
    private fun register(radioOption:Int, email:String)
    {
        if(radioOption==volunteer) {
            val transmit = Intent(this@LoginActivity, OrganisationListActivity::class.java)
            val info= Bundle()
            info.putInt(key_auth, register)
            info.putInt(key_type, volunteer)
            info.putString(key_email, email)
            transmit.putExtras(info)
            startActivity(transmit)
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
            radioNGO.id->ngo
            radioVolunteer.id->volunteer
            else->-1
        }
        val email:String=etEmail.text.toString()
        val password:String=etPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty())
        {
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@LoginActivity, "Sign In Successful", Toast.LENGTH_SHORT).show()
                        success=checkSuccess()
                    }
                }
                catch(e: Error)
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
//                        when(e.localizedMessage)
//                        {
//                            "auth/email-already-exists"-> Toast.makeText(this@LoginActivity, "User $email has already Registered. Please Sign In", Toast.LENGTH_SHORT).show()
//                            "auth/invalid-email"-> Toast.makeText(this@LoginActivity, "The mail-id entered $email is invalid. Try Again", Toast.LENGTH_SHORT).show()
//                            "auth/invalid-password"-> Toast.makeText(this@LoginActivity, "The password entered is invalid (Minimum 6 characters). Try Again", Toast.LENGTH_SHORT).show()
//                            else-> Toast.makeText(this@LoginActivity, "Login Error. Please Try Again", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
                if(success)
                    signIn(radioOption, email)
            }
        }
    }
    private fun signIn(radioOption:Int, email:String)
    {
        if(radioOption==volunteer) {
            val transmit = Intent(this@LoginActivity, OrganisationListActivity::class.java)
            val info= Bundle()
            info.putInt(key_auth, signin)
            info.putInt(key_type, volunteer)
            info.putString(key_email, email)
            transmit.putExtras(info)
            startActivity(transmit)
        }
    }
    private fun checkSuccess():Boolean{
        if(auth.currentUser!=null)
            return true
        return false
    }

}