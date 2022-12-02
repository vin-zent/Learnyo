package com.careerpath.learnyo.learnonleranyo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.careerpath.learnyo.R
import com.careerpath.learnyo.Utils.FirebaseUtils.firebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var verificationId: String


    lateinit var progressBar: ProgressBar
    lateinit var editText: TextInputEditText
    lateinit var verifyBtn: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        progressBar = findViewById(R.id.progressbar)
        editText = findViewById(R.id.editTextCode)

        val phoneNumber = intent.getStringExtra("phoneNumber")
        if (phoneNumber != null) {
            sendVerificationCode(phoneNumber)
        }

        verifyBtn = findViewById(R.id.verifybtn)
        verifyBtn.setOnClickListener {
            val code = editText.text.toString().trim()

            if (code.isEmpty() || code.length < 6) {
                editText.error = "Enter code..."
                editText.requestFocus()
                return@setOnClickListener
            }
            verifyCode(code)

        }


    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }
    private fun signInWithCredential(credential: PhoneAuthCredential) {


        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun sendVerificationCode(phoneNumber: String) {
        progressBar.visibility = View.VISIBLE
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                    super.onCodeSent(s, forceResendingToken)
                    verificationId = s
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    val code = phoneAuthCredential.smsCode
                    if (code != null) {
                        editText.setText(code)
                        verifyCode(code)
                    }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        progressBar.visibility = View.GONE
    }
}