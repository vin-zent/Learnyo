package com.careerpath.learnyo.teachonlearnyo.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.Adapter.CourseAdapter
import com.careerpath.learnyo.learnonleranyo.Adapter.contentAdapter
import com.careerpath.learnyo.learnonleranyo.Model.Content
import com.careerpath.learnyo.learnonleranyo.Model.Course
import com.google.firebase.firestore.*


class ViewFragment : Fragment() {

    lateinit var recViewcontent: RecyclerView
    lateinit var contAdapter: contentAdapter
    lateinit var allcontent:ArrayList<Content>
    lateinit var uniquenameofcourse : String
    lateinit var nameTopic : String
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view, container, false)
        uniquenameofcourse = requireActivity().intent.extras!!.getString("nameunique").toString()
        nameTopic = requireActivity().intent.extras!!.getString("currentTopic").toString()


        recViewcontent = view.findViewById(R.id.viewrec)
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout)
        allcontent=arrayListOf()
        recViewcontent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recViewcontent.setHasFixedSize(true)
        contAdapter = contentAdapter(allcontent)
        recViewcontent.adapter = contAdapter






        ChangeListener(uniquenameofcourse,nameTopic)
        swipeRefreshLayout.setOnRefreshListener {

            recViewcontent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recViewcontent.setHasFixedSize(true)
            contAdapter = contentAdapter(allcontent)
            recViewcontent.adapter = contAdapter

        }


        return view
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