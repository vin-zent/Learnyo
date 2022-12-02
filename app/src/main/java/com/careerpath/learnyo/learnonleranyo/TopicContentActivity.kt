package com.careerpath.learnyo.learnonleranyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.Adapter.contentAdapter
import com.careerpath.learnyo.learnonleranyo.Model.Content
import com.google.firebase.firestore.*

class TopicContentActivity : AppCompatActivity() {

    lateinit var recViewcontent: RecyclerView
    lateinit var contAdapter: contentAdapter
    lateinit var allcontent:ArrayList<Content>

    lateinit var uniquenameofcourse : String
    lateinit var nameTopic : String
    lateinit var Topiccontent : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_content)

        recViewcontent = findViewById(R.id.contrecycle)
        allcontent=arrayListOf()
        Topiccontent = findViewById(R.id.topiccfull)



        uniquenameofcourse = intent.getStringExtra("nameUnique").toString()
        nameTopic = intent.getStringExtra("currenttopic").toString()

        Topiccontent.setText(nameTopic)




        ChangeListener(uniquenameofcourse,nameTopic)
        recViewcontent.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recViewcontent.setHasFixedSize(true)
        contAdapter = contentAdapter(allcontent)
        recViewcontent.adapter = contAdapter
    }

    private fun ChangeListener(uniquenameofcou: String, nametopic: String) {

        FirebaseFirestore.getInstance().collection("Course").document(uniquenameofcou).collection(uniquenameofcou).document(nametopic).collection(nametopic).orderBy("time", Query.Direction.ASCENDING).addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        allcontent.add(dc.document.toObject(Content::class.java))
                    }
                }
                contAdapter.notifyDataSetChanged()

            }
        })
    }
}