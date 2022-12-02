package com.careerpath.learnyo.teachonlearnyo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.careerpath.learnyo.R



class draftTeachFrag : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_draft_teach, container, false)

        val button: Button = view.findViewById(R.id.button2)
        button.setOnClickListener {
            Toast.makeText(
                activity,
                "This is draft ",
                Toast.LENGTH_SHORT
            ).show()
        }

        return view
    }


}