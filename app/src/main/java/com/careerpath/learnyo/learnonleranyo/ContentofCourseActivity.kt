package com.careerpath.learnyo.learnonleranyo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.Adapter.CourseAdapter
import com.careerpath.learnyo.learnonleranyo.Adapter.LearnTopicAdapter
import com.careerpath.learnyo.learnonleranyo.Model.Course
import com.careerpath.learnyo.learnonleranyo.Model.Topicmodel
import com.careerpath.learnyo.teachonlearnyo.Model.Topic
import com.google.firebase.firestore.*

class ContentofCourseActivity : AppCompatActivity() {
    lateinit var uniqueids: String
    lateinit var txtcoursename: String
    lateinit var allTopics:ArrayList<Topicmodel>

    lateinit var textcoursename : TextView
    lateinit var Topicrecycler :RecyclerView
    lateinit var learnTopicAdapter: LearnTopicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contentof_course)

        textcoursename =findViewById(R.id.coursefull)
        Topicrecycler =findViewById(R.id.topicRecView)
        txtcoursename = intent.getStringExtra("courseeName").toString()
        uniqueids = intent.getStringExtra("uniqueid").toString()
        textcoursename.setText(txtcoursename)

        allTopics=arrayListOf()

        TopicChangeListener()

        Topicrecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        Topicrecycler.setHasFixedSize(true)
        learnTopicAdapter = LearnTopicAdapter(allTopics,applicationContext as Context,uniqueids,txtcoursename)
        Topicrecycler.adapter = learnTopicAdapter




    }
    private fun TopicChangeListener() {

        FirebaseFirestore.getInstance().collection("Course").document(uniqueids).collection(uniqueids).addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        allTopics.add(dc.document.toObject(Topicmodel::class.java))
                    }
                }
                learnTopicAdapter.notifyDataSetChanged()

            }
        })
    }
}