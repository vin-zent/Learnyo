package com.careerpath.learnyo.learnonleranyo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.careerpath.learnyo.R

class CallerAuthActivity : AppCompatActivity() {
    lateinit var continueBtn : Button
    lateinit var editTextPhone : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caller_auth)

        continueBtn = findViewById(R.id.continuebtn)
        editTextPhone = findViewById(R.id.indinphone)

        continueBtn.setOnClickListener {

            val number: String = editTextPhone.getText().toString().trim()

            if (number.isEmpty() || number.length < 10) {
                editTextPhone.setError("Valid number is required")
                editTextPhone.requestFocus()

            }else{
                val phoneNumber = "+91$number"

                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("phoneNumber", phoneNumber)
                startActivity(intent)
            }


        }
    }
}