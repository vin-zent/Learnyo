package com.careerpath.learnyo.teachonlearnyo.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.careerpath.learnyo.R
import com.careerpath.learnyo.Utils.FirebaseUtils
import com.careerpath.learnyo.learnonleranyo.Model.Content
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 1
    private var ImageUri: Uri? = null


    lateinit var ExternalLayout: LinearLayout
    lateinit var addExternaltext : TextView
    lateinit var Topiccc : TextView

    lateinit var etContent : EditText
    lateinit var addContent : Button
    lateinit var clearBtn : TextView

    lateinit var chooseImage : Button
    lateinit var addImage : Button
    lateinit var choosedimageview : ImageView
    private val userCollectionRef = Firebase.firestore.collection("Course")

    lateinit var uniquenameofcourse : String
    lateinit var nameTopic : String


    lateinit var etHeading : EditText
    lateinit var addheading : Button
    lateinit var clearheading: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        ExternalLayout = view.findViewById(R.id.visiblelay)
        addExternaltext = view.findViewById(R.id.AddExternal)

        Topiccc = view.findViewById(R.id.topiccc)

        //heading
        etHeading = view.findViewById(R.id.heading)
        addheading = view.findViewById(R.id.heading_Add)
        clearheading = view.findViewById(R.id.heading_clear)

//        contentadd
        etContent = view.findViewById(R.id.contentet)
        addContent = view.findViewById(R.id.content_Add)
        clearBtn = view.findViewById(R.id.btn_clear)

//        imageAdd

        chooseImage = view.findViewById(R.id.image_choose)
        addImage = view.findViewById(R.id.image_Add)
        choosedimageview = view.findViewById(R.id.uploadedchooose)



        uniquenameofcourse = requireActivity().intent.extras!!.getString("nameunique").toString()
        nameTopic = requireActivity().intent.extras!!.getString("currentTopic").toString()

        Topiccc.setText(nameTopic)


//        headingadd
        addheading.setOnClickListener {
            storeHeadingData(uniquenameofcourse,nameTopic)
            etHeading.setText("")

        }
        clearheading.setOnClickListener {
            etHeading.setText("")

        }

//        contentadddd
        addContent.setOnClickListener {
            storecontentData(uniquenameofcourse,nameTopic)
            etContent.setText("")

        }
        clearBtn.setOnClickListener {
            etContent.setText("")

        }

//        add image

        chooseImage.setOnClickListener {
            openFileChooser()
        }

        addImage.setOnClickListener {
            uploadcontent(uniquenameofcourse,nameTopic)

        }


//      External
        addExternaltext.setOnClickListener {
            ExternalLayout.visibility = View.VISIBLE
        }



        return view
    }

    private fun storeHeadingData( uniqename: String, topicname: String) = CoroutineScope(Dispatchers.IO).launch {

        val headingTxt : String = etHeading.text.toString()
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter).toString()

        try {
            val contenttxtDet = Content(headingTxt,".",".",formatted)
            userCollectionRef.document(uniqename).collection(uniqename).document(topicname).collection(topicname).document(formatted).set(contenttxtDet).await()

            withContext(Dispatchers.Main){
                Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show()

            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context, ""+ e.message.toString(), Toast.LENGTH_SHORT).show()

            }
        }


    }

    private fun uploadcontent(uniquenameofcou: String, nametopic: String) = CoroutineScope(Dispatchers.IO).launch {

        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter).toString()


        try {
            ImageUri?.let {

                val storageReference : UploadTask.TaskSnapshot? = FirebaseUtils.firebaseStore.getReference("images/$formatted").putFile(ImageUri!!).await()
                storageReference
                withContext(Dispatchers.Main) {

                    val urii : String = storageReference?.storage?.downloadUrl?.await().toString()

                    val contentimageDet = Content(".",urii,".",formatted)
                    storeCourseData(contentimageDet,uniquenameofcou,nametopic)
                    Toast.makeText(context, "Successfully uploaded image", Toast.LENGTH_SHORT).show()

                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            data?.data?.let {
                ImageUri = it
                choosedimageview.setImageURI(it)
            }
        }

    }

    private fun storeCourseData(imageurii: Content, uniqename: String, topicname: String) = CoroutineScope(Dispatchers.IO).launch {
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter).toString()

        try {
            userCollectionRef.document(uniqename).collection(uniqename).document(topicname).collection(topicname).document(formatted).set(imageurii).await()

            withContext(Dispatchers.Main){
                Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show()

            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context, ""+ e.message.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun storecontentData( uniqename: String, topicname: String) = CoroutineScope(Dispatchers.IO).launch {

        val contentTxt : String = etContent.text.toString()
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter).toString()

        try {
            val contenttxtDet = Content(".",".",contentTxt,formatted)
            userCollectionRef.document(uniqename).collection(uniqename).document(topicname).collection(topicname).document(formatted).set(contenttxtDet).await()

            withContext(Dispatchers.Main){
                Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show()

            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context, ""+ e.message.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }

}