package com.careerpath.learnyo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.careerpath.learnyo.learnonleranyo.LoginActivity
import com.careerpath.learnyo.teachonlearnyo.TeachHomeActivity
import com.careerpath.learnyo.teachonlearnyo.teacherloginActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class JustSelectActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    var storage: FirebaseStorage = FirebaseStorage.getInstance()

    var storageRef: StorageReference = storage.getReferenceFromUrl("gs://learnyo.appspot.com/buljustimg.png")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_just_select)

        imageView = findViewById(R.id.justimg)


        storageRef.getDownloadUrl()
            .addOnSuccessListener(OnSuccessListener<Any> { uri -> // Got the download URL for 'users/me/profile.png'
                Glide.with(this)
                    .load(uri.toString())
                    .into(imageView)
            }).addOnFailureListener(OnFailureListener {
                // Handle any errors
            })


        val learnBtn: Button = findViewById(R.id.learnStudent)
        learnBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val teachBtn: Button = findViewById(R.id.teach)
        teachBtn.setOnClickListener {
            val intent = Intent(this, teacherloginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}