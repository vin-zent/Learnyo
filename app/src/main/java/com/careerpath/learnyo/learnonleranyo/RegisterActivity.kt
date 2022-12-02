package com.careerpath.learnyo.learnonleranyo

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.careerpath.learnyo.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var emailEt: EditText
    private lateinit var passEt: EditText
    private lateinit var CpassEt: EditText
    lateinit var signUpBtn : Button

    private val userCollectionRef = Firebase.firestore.collection("Users")
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var progressDialog: ProgressDialog

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    // Creating firebaseAuth object


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signUpBtn = findViewById(R.id.regBtn)
        fullName = findViewById(R.id.editTextTextPersonName)
        emailEt = findViewById(R.id.editTextTextEmailAddress)
        passEt = findViewById(R.id.editTextTextPassword)
        CpassEt = findViewById(R.id.editTextTextPassword2)
        val signInTv = findViewById<TextView>(R.id.signInid)

        progressDialog = ProgressDialog(this)

        textAutoCheck()


        signInTv.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        signUpBtn.setOnClickListener {
            checkInput()

        }

    }

    private fun textAutoCheck() {

        fullName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (fullName.text.isEmpty()){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (fullName.text.length >= 4){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (count >= 4){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }
        })

        emailEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (emailEt.text.isEmpty()){
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (emailEt.text.matches(emailPattern.toRegex())) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (emailEt.text.matches(emailPattern.toRegex())) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }
        })

        passEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (passEt.text.isEmpty()){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (passEt.text.length > 5){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (count > 5){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }
        })

        CpassEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (CpassEt.text.isEmpty()){
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (CpassEt.text.toString() == passEt.text.toString()){
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (CpassEt.text.toString() == passEt.text.toString()){
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                }
            }
        })

    }

    private fun checkInput() {
        if (fullName.text.isEmpty()){
            Toast.makeText(this, "Name can't empty!", Toast.LENGTH_SHORT).show()

            return
        }
        if (emailEt.text.isEmpty()){
            Toast.makeText(this, "Email can't empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!emailEt.text.matches(emailPattern.toRegex())) {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show()
            return
        }
        if(passEt.text.isEmpty()){
            Toast.makeText(this, "Password can't empty!", Toast.LENGTH_SHORT).show()
            return
        }
        if (passEt.text.toString() != CpassEt.text.toString()){
            Toast.makeText(this, "Password not Match", Toast.LENGTH_SHORT).show()
            return
        }

        signUp()


    }

    private fun signUp() {

        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account")
        progressDialog.show()

        val emailV: String = emailEt.text.toString()
        val passV: String = passEt.text.toString()
        val fullname : String = fullName.text.toString()


        /*create a user*/
        firebaseAuth.createUserWithEmailAndPassword(emailV,passV)

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.setMessage("Save User Data")


                    val user = com.careerpath.learnyo.learnonleranyo.Model.User(fullname,"",firebaseAuth.uid.toString(),emailV,"","")

                    storeUserData(user)

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this, "failed to Authenticate !", Toast.LENGTH_SHORT).show()
                }
            }

    }



    private fun storeUserData(user: com.careerpath.learnyo.learnonleranyo.Model.User) = CoroutineScope(Dispatchers.IO).launch {
        try {

            userCollectionRef.document(firebaseAuth.uid.toString()).set(user).await()
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, "Data saved", Toast.LENGTH_SHORT).show()

                progressDialog.dismiss()
            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, ""+ e.message.toString(), Toast.LENGTH_SHORT).show()

                progressDialog.dismiss()
            }
        }
    }

}
