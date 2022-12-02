package com.careerpath.learnyo.teachonlearnyo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.careerpath.learnyo.Utils.FirebaseUtils.firebaseStore
import com.careerpath.learnyo.learnonleranyo.CoursedescriptionActivity
import com.careerpath.learnyo.learnonleranyo.loadingDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AddnewCourseActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var mImageUri: Uri? = null

    private lateinit var imageRef : StorageReference
    private val userCollectionRef = Firebase.firestore.collection("Course")


    var Language = arrayOf(
        "Android",
        "web development",
        "back-end",
        "UI/UX designing",
        "Graphic designing",
        "Communication"
    )
    lateinit var spin: Spinner

    lateinit var fullName: EditText
    lateinit var shortName: EditText
    lateinit var unique: EditText

    lateinit var loadingDialog: loadingDialog
    lateinit var contentWhat: EditText
    lateinit var contentwho: EditText
    lateinit var contbenefits: EditText


    lateinit var category :String

    lateinit var uploadedImg: ImageView

    lateinit var chooseBtn: Button

    lateinit var uploadBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.careerpath.learnyo.R.layout.activity_addnew_course)


        loadingDialog = loadingDialog(this)


        category=""
        spin = findViewById(com.careerpath.learnyo.R.id.spinner)
        uploadBtn = findViewById(com.careerpath.learnyo.R.id.uploadButton)
        chooseBtn = findViewById(com.careerpath.learnyo.R.id.btnchoose)
        uploadedImg = findViewById(com.careerpath.learnyo.R.id.uploadedimg)

        fullName = findViewById(com.careerpath.learnyo.R.id.fullname)
        unique = findViewById(com.careerpath.learnyo.R.id.uniqid)
        shortName = findViewById(com.careerpath.learnyo.R.id.shortname)

        contentWhat = findViewById(com.careerpath.learnyo.R.id.whatcontent)
        contentwho = findViewById(com.careerpath.learnyo.R.id.whocontent)
        contbenefits = findViewById(com.careerpath.learnyo.R.id.benefitcontent)
        spin.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Language)



        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                category= spin.selectedItem.toString()
            }
        }

        chooseBtn.setOnClickListener {

            openFileChooser()
        }

        uploadBtn.setOnClickListener {

            val filename : String = unique.text.toString()
            uploadFile(filename)
            val intent = Intent(this, TopicAddActivity::class.java)
            intent.putExtra("uniquetoTopic", unique.text.toString())
            intent.putExtra("Coursename", fullName.text.toString().uppercase())

            startActivity(intent)
        }


    }

//    private fun getFileExtension(uri: Uri): String? {
//        val cR = contentResolver
//        val mime = MimeTypeMap.getSingleton()
//        return mime.getExtensionFromMimeType(cR.getType(uri))
//    }

    private fun uploadFile(filename: String) = CoroutineScope(Dispatchers.IO).launch {

        val fullname : String = fullName.text.toString().uppercase()
        val uniqueid : String = unique.text.toString()
        val shortname :String = shortName.text.toString()
        val what : String = contentWhat.text.toString()
        val who :String = contentwho.text.toString()
        val bene : String = contbenefits.text.toString()
        val categoryname:String = spin.selectedItem.toString()

        try {
            mImageUri?.let {

                val storageReference : UploadTask.TaskSnapshot? = firebaseStore.getReference("images/$filename").putFile(mImageUri!!).await()

                withContext(Dispatchers.Main) {

                    val urii : String = storageReference?.storage?.downloadUrl?.await().toString()

                    val courseDet = com.careerpath.learnyo.learnonleranyo.Model.Course(fullname,shortname,categoryname,uniqueid,urii,what,who,bene)
                    storeCourseData(courseDet)
                    Toast.makeText(applicationContext, "Successfully uploaded image", Toast.LENGTH_SHORT).show()

                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()

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
                mImageUri = it
                uploadedImg.setImageURI(it)
            }
        }

    }

    private fun storeCourseData(coursename: com.careerpath.learnyo.learnonleranyo.Model.Course) = CoroutineScope(Dispatchers.IO).launch {
        val uniqename : String = unique.text.toString()

        try {

            userCollectionRef.document(uniqename).set(coursename).await()
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, "Data saved", Toast.LENGTH_SHORT).show()

            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, ""+ e.message.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }
}


