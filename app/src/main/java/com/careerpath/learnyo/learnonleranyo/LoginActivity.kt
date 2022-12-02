package com.careerpath.learnyo.learnonleranyo

import android.content.Intent
import android.graphics.Color
import android.icu.util.TimeUnit.values
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.careerpath.learnyo.R
import com.careerpath.learnyo.Utils.FirebaseUtils.firebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.GoogleAuthProvider
import java.time.chrono.JapaneseEra.values

class LoginActivity : AppCompatActivity() {

    lateinit var loginBtn: Button
    lateinit var signUptxt : TextView
    lateinit var callerBtn  : ImageView
    lateinit var gmailBtn  : ImageView
    lateinit var facebookBtn  : ImageView
    lateinit var emailEt : EditText
    lateinit var passEt :EditText
    lateinit var emailError:TextView
    lateinit var passwordError:TextView

//    lateinit var mGoogleSignInClient: GoogleSignInClient
//     private val Req_Code: Int = 123

    lateinit var email: String
    lateinit var password: String

    lateinit var loadingDialog: loadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //editText
        emailEt = findViewById(R.id.editTextTextEmailAddress)
        passEt = findViewById(R.id.editTextTextPassword)

        loadingDialog = loadingDialog(this)

        //error message
        emailError = findViewById(R.id.emailError)
        passwordError = findViewById(R.id.passwordError)

        textAutoCheck() // function(below) to check the entered text

        //loginbtn
        loginBtn= findViewById(R.id.loginStudent)
        loginBtn.setOnClickListener {


            email = emailEt.getText().toString().trim()
            password = passEt.getText().toString().trim()

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) ){
                val snackBar = Snackbar.make(
                    it, "Please enter login details",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackBar.setActionTextColor(Color.WHITE)
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(Color.parseColor("#2E3B62"))
                val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                snackBar.show()

            }else{
                checkInput() //check input format is correct
            }

        }

        //signup textview
        signUptxt = findViewById(R.id.signup)
        signUptxt.setOnClickListener {
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        //caller imagview

        callerBtn = findViewById(R.id.caller)
        callerBtn.setOnClickListener {
            var intent = Intent(this,CallerAuthActivity::class.java)
            startActivity(intent)
        }

        //email imageview
        gmailBtn= findViewById(R.id.gmail)
        FirebaseApp.initializeApp(this)

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("675405029655-b9hc2n3cv3i3c46mm4oqs6vkocfpgs5r.apps.googleusercontent.com")
//            .requestEmail()
//            .build()

//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

//        gmailBtn.setOnClickListener {
//            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
//            signInGoogle()
//        }



        //facebook imageview
        facebookBtn= findViewById(R.id.facebook)


    }

//    private fun signInGoogle() {
//        val signInIntent: Intent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, Req_Code)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Req_Code) {
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleResult(task)
//        }
//    }

//    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
//            if (account != null) {
//                UpdateUI(account)
//            }
//        } catch (e: ApiException) {
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
//        }
//    }

//    private fun UpdateUI(account: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//
////                SavedPreference.setEmail(this, account.email.toString())
////                SavedPreference.setUsername(this, account.displayName.toString())
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }

    private fun textAutoCheck() {



        emailEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (emailEt.text.isEmpty()){
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                    emailError.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)
                    emailError.visibility = View.GONE
                }
            }
        })

        passEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (passEt.text.isEmpty()){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (passEt.text.length > 4){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                passwordError.visibility = View.GONE
                if (count > 4){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,R.drawable.ic_check), null)

                }
            }
        })



    }

    private fun checkInput() {

        if (emailEt.text.isEmpty()){
            emailError.visibility = View.VISIBLE
            emailError.text = "Email Can't be Empty"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
            emailError.visibility = View.VISIBLE
            emailError.text = "Enter Valid Email"
            return
        }
        if(passEt.text.isEmpty()){
            passwordError.visibility = View.VISIBLE
            passwordError.text = "Password Can't be Empty"
            return
        }

        if ( passEt.text.isNotEmpty() && emailEt.text.isNotEmpty()){
            emailError.visibility = View.GONE
            passwordError.visibility = View.GONE
            signInUser()
        }
    }

    private fun signInUser() {

        loadingDialog.startLoadingDialog()
        email = emailEt.getText().toString().trim()
        password = passEt.getText().toString().trim()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { signIn->

            if (signIn.isSuccessful){
                loadingDialog.dismissDialog()
                startActivity(Intent(this, HomeActivity::class.java))
                val msg= "signed in successfully"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


                finish()
            } else {
                val msg = "sign in failed"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                loadingDialog.dismissDialog()
            }
        }
    }


}