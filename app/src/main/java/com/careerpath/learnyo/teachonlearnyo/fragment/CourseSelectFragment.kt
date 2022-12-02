package com.careerpath.learnyo.teachonlearnyo.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.careerpath.learnyo.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class CourseSelectFragment : Fragment() {

    lateinit var book: ImageView
    lateinit var simplecourseBtn :Button
    lateinit var roadBtn :Button
    lateinit var close: ImageView

    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.reference.child("image/book.png")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View =inflater.inflate(R.layout.fragment_course_select, container, false)

        book = view.findViewById(R.id.book)
        simplecourseBtn = view.findViewById(R.id.simplecourse)
        roadBtn = view.findViewById(R.id.roadmap)
        close = view.findViewById(R.id.close)

        val localfile = File.createTempFile("tempImagee","png")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap= BitmapFactory.decodeFile(localfile.absolutePath)
            book.setImageBitmap(bitmap)

        }.addOnFailureListener{
            Toast.makeText(context, "An error occur", Toast.LENGTH_SHORT).show()
        }



        return view
    }

}