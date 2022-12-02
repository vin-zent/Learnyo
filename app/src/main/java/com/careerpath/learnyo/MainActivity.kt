package com.careerpath.learnyo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.careerpath.learnyo.Utils.FirebaseUtils
import com.careerpath.learnyo.learnonleranyo.HomeActivity
import com.careerpath.learnyo.learnonleranyo.LoginActivity
import com.careerpath.learnyo.teachonlearnyo.TeachHomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            checkUser()


        }, 1000)


    }
    private fun checkUser() {

//        if(FirebaseUtils.firebaseUser?.isEmailVerified == true){
//            val intent = Intent(this, HomeActivity::class.java)
//            Log.d("tag","its in email")
//            startActivity(intent)
//
//            finish()

        if(GoogleSignIn.getLastSignedInAccount(this) != null) {
            val intent = Intent(this, TeachHomeActivity::class.java)
            Log.d("tag","its in google")

            startActivity(intent)
            finish()
        }else if(FirebaseUtils.firebaseUser != null){
            val intent = Intent(this, HomeActivity::class.java)
            Log.d("tag","its in email")
            startActivity(intent)
            finish()
        }
        if(FirebaseUtils.firebaseUser == null){
            val intent = Intent(this, JustSelectActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
