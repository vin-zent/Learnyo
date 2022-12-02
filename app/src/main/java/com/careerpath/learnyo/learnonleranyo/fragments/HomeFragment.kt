package com.careerpath.learnyo.learnonleranyo.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.careerpath.learnyo.learnonleranyo.Adapter.CourseAdapter
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.Adapter.verticalcourseAdapter
import com.careerpath.learnyo.learnonleranyo.Model.Course
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class HomeFragment : Fragment() {

    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.reference.child("image/learn.jpg")

    lateinit var imageVie : ImageView
    lateinit var recView:RecyclerView
    lateinit var vertirecView:RecyclerView
    lateinit var allCourses:ArrayList<Course>
    lateinit var courseAdapter: CourseAdapter

    lateinit var verticourseAdapter: verticalcourseAdapter


    val imageRef = Firebase.storage.reference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        imageVie = view.findViewById(R.id.coverimage)
        recView = view.findViewById(R.id.newRecView)
        vertirecView = view.findViewById(R.id.verticalRecView)



//        newRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
//        newRecView.setHasFixedSize(true)

        allCourses=arrayListOf()

        storageRef.getDownloadUrl()
            .addOnSuccessListener(OnSuccessListener<Any> { uri -> // Got the download URL for 'users/me/profile.png'
                Glide.with(this)
                    .load(uri.toString())
                    .into(imageVie)
            }).addOnFailureListener(OnFailureListener {
                // Handle any errors
            })



        EventChangeListener()
        recView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        recView.setHasFixedSize(true)
        courseAdapter = CourseAdapter(allCourses,activity as Context)
        recView.adapter = courseAdapter



        vertirecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        vertirecView.setHasFixedSize(true)
        verticourseAdapter = verticalcourseAdapter(allCourses,activity as Context)
        vertirecView.adapter = verticourseAdapter


        return view

    }

    private fun newCourseFiles() = CoroutineScope(Dispatchers.IO).launch {
        try {
//            val images = imageRef.child("images/").listAll().await()
//            val imageUrls = mutableListOf<String>()
//            for(image in images.items) {
//                val url = image.downloadUrl.await()
//                imageUrls.add(url.toString()) }
            withContext(Dispatchers.Main) {
                val imageAdapter = CourseAdapter(allCourses,activity as Context)
                newRecView.apply {
                    adapter = imageAdapter
                    layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                }
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun EventChangeListener() {

        FirebaseFirestore.getInstance().collection("Course").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        allCourses.add(dc.document.toObject(Course::class.java))
                    }
                }
                courseAdapter.notifyDataSetChanged()
                verticourseAdapter.notifyDataSetChanged()


            }
        })
    }


}


