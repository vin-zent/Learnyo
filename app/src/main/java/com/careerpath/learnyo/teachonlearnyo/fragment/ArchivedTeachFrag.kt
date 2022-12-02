package com.careerpath.learnyo.teachonlearnyo.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment




class ArchivedTeachFrag : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view: View = LayoutInflater.from(context).inflate(com.careerpath.learnyo.R.layout.fragment_archived_teach, container, false)

        val button: Button = view.findViewById(com.careerpath.learnyo.R.id.button)
        button.setOnClickListener {
            Toast.makeText(
                activity,
                "This is Archive ",
                Toast.LENGTH_SHORT
            ).show()
        }

        return view
    }


}