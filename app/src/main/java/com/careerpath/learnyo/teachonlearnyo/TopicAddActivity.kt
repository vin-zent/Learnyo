package com.careerpath.learnyo.teachonlearnyo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.careerpath.learnyo.R
import com.careerpath.learnyo.Utils.FirebaseUtils
import com.careerpath.learnyo.learnonleranyo.Model.Topicmodel
import com.careerpath.learnyo.teachonlearnyo.Adapter.TopicAdapter
import com.careerpath.learnyo.teachonlearnyo.Model.Topic
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TopicAddActivity : AppCompatActivity() {

    lateinit var etTopic :EditText
    lateinit var addTopicBtn : Button
    lateinit var recyclerview :RecyclerView
    lateinit var uniqueid : String
    lateinit var courseName : String
    lateinit var textviewCourse :TextView
    private val userCollection = Firebase.firestore.collection("Course")
    val data = ArrayList<Topic>()
    var list: ArrayList<String> = ArrayList()
    val updateMap: MutableMap<String,Any> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_add)

        etTopic = findViewById(R.id.topicset)
        addTopicBtn = findViewById(R.id.addtopicbtn)
        recyclerview = findViewById(R.id.topicrecycle)
        textviewCourse = findViewById(R.id.coursefullname)
        uniqueid = intent.getStringExtra("uniquetoTopic").toString()
        courseName = intent.getStringExtra("Coursename").toString()

        textviewCourse.setText(courseName)
        recyclerview.layoutManager = LinearLayoutManager(this)



        addTopicBtn.setOnClickListener {

            val topic :String = etTopic.text.toString().uppercase()

            if (etTopic.text.toString().equals("")){
                etTopic.setError("Enter a topic")
            }else{

                data.add(Topic(topic))
                val adapter = TopicAdapter(data,this,uniqueid)

                adapter.notifyDataSetChanged()

                recyclerview.adapter = adapter

                uploadFile(uniqueid,topic)
                etTopic.setText("")
            }

        }

    }

    private fun uploadFile(uniq: String,topicc :String) = CoroutineScope(Dispatchers.IO).launch {

//        val fullname : String = fullName.text.toString().uppercase()
//        val uniqueid : String = unique.text.toString()
//        val shortname :String = shortName.text.toString()
//        val what : String = contentWhat.text.toString()
//        val who :String = contentwho.text.toString()
//        val bene : String = contbenefits.text.toString()
//        val categoryname:String = spin.selectedItem.toString()

        try {
            topicc.let {


                withContext(Dispatchers.Main) {


                    val topicDet = Topicmodel(topicc,uniq)
                    addTopictofirestore(uniq,topicc,topicDet)


                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun addTopictofirestore(uniqename : String,topicname : String,update: Topicmodel) = CoroutineScope(Dispatchers.IO).launch {
        try {

            userCollection.document(uniqename).collection(uniqename).document(topicname).set(update).await()
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, "Topic saved", Toast.LENGTH_SHORT).show()

            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, ""+ e.message.toString(), Toast.LENGTH_SHORT).show()
                Log.d("rr",""+ e.message.toString())

            }
        }
    }
}