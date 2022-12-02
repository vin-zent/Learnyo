package com.careerpath.learnyo.learnonleranyo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.careerpath.learnyo.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.course_view.view.*

class CoursedescriptionActivity : AppCompatActivity() {
    lateinit var detailCourseimg : ImageView
    lateinit var whatcontent : TextView
    lateinit var whoContent : TextView
    lateinit var benefitcontent : TextView
    var documentReference: DocumentReference? = null
    lateinit var fStore: FirebaseFirestore
    lateinit var uniqueides: String
    var courseename :String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discriptioncontent)
        fStore = FirebaseFirestore.getInstance()

        uniqueides = intent.getStringExtra("uniqueid").toString()

        detailCourseimg = findViewById(R.id.coverimagedetail)
        whatcontent = findViewById(R.id.whatcont)
        whoContent = findViewById(R.id.whocont)
        benefitcontent = findViewById(R.id.benefitcont)

        readCurrentdescription()

        val letsgo : Button = findViewById(R.id.letsgo)
        letsgo.setOnClickListener {

            val intent = Intent(this,ContentofCourseActivity::class.java)
            intent.putExtra("uniqueid", uniqueides)
            intent.putExtra("courseeName", courseename)

            startActivity(intent)
        }

    }
    private fun readCurrentdescription() {
        documentReference = fStore.collection("Course").document(uniqueides)
        documentReference!!.get()
            .addOnSuccessListener(OnSuccessListener<DocumentSnapshot> { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    Glide.with(this).load(documentSnapshot.getString("imageUri")).into(detailCourseimg)

                    whatcontent.setText( documentSnapshot.getString("whatwill"))
                    whoContent.setText( documentSnapshot.getString("whoare"))
                    benefitcontent.setText( documentSnapshot.getString("benefitof"))
                    courseename = documentSnapshot.getString("courseFullname")
                } else {
                    Log.d("tag", "onEvent: Document do not exists")
                }
            }).addOnFailureListener(OnFailureListener {
                Log.d(
                    "tag",
                    "onEvent: Failed to fetch Data"
                )
            })
    }
}