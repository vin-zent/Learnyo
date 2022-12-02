package com.careerpath.learnyo.teachonlearnyo.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentManager.TAG
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.HomeActivity
import com.careerpath.learnyo.teachonlearnyo.AddnewCourseActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class HomeTeachfrag : Fragment() {

    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.reference.child("image/teach.png")

    lateinit var imageTeach: ImageView

    lateinit var courseBtn: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_teachfrag, container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        imageTeach = view.findViewById(R.id.appCompatImageView)
        courseBtn = view.findViewById(R.id.coursecreate)


        val localfile = File.createTempFile("tempImag","png")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap= BitmapFactory.decodeFile(localfile.absolutePath)
            imageTeach?.setImageBitmap(bitmap)

        }.addOnFailureListener{
            Toast.makeText(context, "An error occur", Toast.LENGTH_SHORT).show()
        }



        courseBtn.setOnClickListener {
            val intent = Intent(activity, AddnewCourseActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}