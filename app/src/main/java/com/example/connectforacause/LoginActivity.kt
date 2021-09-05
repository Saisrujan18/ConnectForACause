package com.example.connectforacause

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val register:Int=0
    private val signin:Int=1

    private var success:Boolean=false

    //transmit and receive keys stored in strings.xml

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(checkSuccess())
//            signIn(1, (auth.currentUser?.email)?:"")
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_auth)
        auth = FirebaseAuth.getInstance()
        btnRegister.setOnClickListener{ registerUser() }
        btnSignIn.setOnClickListener{ signInUser() }
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
            radioNGO.id->resources.getInteger(R.integer.ngo)
            radioVolunteer.id->resources.getInteger(R.integer.volunteer)
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
                catch(e: FirebaseAuthException)
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
                if(success)
                    register(radioOption, email)
            }
        }
    }
    private fun register(radioOption:Int, email:String)
    {
        if(radioOption==resources.getInteger(R.integer.volunteer)) {
            val transmit = Intent(this@LoginActivity, OrganisationListActivity::class.java)
            val info= Bundle()
            info.putInt(getString(R.string.key_auth), register)
            info.putInt(getString(R.string.key_type), resources.getInteger(R.integer.volunteer))
            info.putString(getString(R.string.key_email), email)
            transmit.putExtras(info)
            startActivity(transmit)
        }
        if(radioOption==resources.getInteger(R.integer.ngo)){
            val transmit = Intent(this@LoginActivity, OrganisationHomeScreen::class.java)
            val info= Bundle()
            info.putInt(getString(R.string.key_HSState), register)
            info.putInt(getString(R.string.key_type), resources.getInteger(R.integer.ngo))
            info.putString(getString(R.string.key_email), email)
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
            radioNGO.id->resources.getInteger(R.integer.ngo)
            radioVolunteer.id->resources.getInteger(R.integer.volunteer)
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
                catch(e: FirebaseAuthException)
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
                if(success)
                    signIn(radioOption, email)
            }
        }
    }
    private fun signIn(radioOption:Int, email:String)
    {
        if(radioOption==resources.getInteger(R.integer.volunteer)) {
            val transmit = Intent(this@LoginActivity, OrganisationListActivity::class.java)
            val info= Bundle()
            info.putInt(getString(R.string.key_auth), signin)
            info.putInt(getString(R.string.key_type), resources.getInteger(R.integer.volunteer))
            info.putString(getString(R.string.key_email), email)
            transmit.putExtras(info)
            startActivity(transmit)
        }
        if(radioOption==resources.getInteger(R.integer.ngo)){
            val transmit = Intent(this@LoginActivity, OrganisationHomeScreen::class.java)
            val info= Bundle()
            info.putInt(getString(R.string.key_HSState), signin)
            info.putInt(getString(R.string.key_type), resources.getInteger(R.integer.ngo))
            info.putString(getString(R.string.key_email), email)
            transmit.putExtras(info)
            startActivity(transmit)
        }
    }
    private fun checkSuccess():Boolean{
        return auth.currentUser!=null
    }

}