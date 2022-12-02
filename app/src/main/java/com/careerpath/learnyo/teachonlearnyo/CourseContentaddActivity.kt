package com.careerpath.learnyo.teachonlearnyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.careerpath.learnyo.R
import com.careerpath.learnyo.teachonlearnyo.fragment.*
import com.google.android.material.tabs.TabLayout

class CourseContentaddActivity : AppCompatActivity() {
    lateinit var topicnamepass:String
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout
    private lateinit var bar: Toolbar
    lateinit var nameunique :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_contentadd)
        pager = findViewById(R.id.viewPager1)
        tab = findViewById(R.id.tabs1)
        bar = findViewById(R.id.toolbar1)
        nameunique = intent.getStringExtra("nameunique").toString()
        topicnamepass = intent.getStringExtra("currentTopic").toString()
        setSupportActionBar(bar)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(EditFragment(), "Edit")
        adapter.addFragment(ViewFragment(), "View")


        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)


    }
}