package com.careerpath.learnyo.teachonlearnyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.careerpath.learnyo.R
import com.careerpath.learnyo.teachonlearnyo.fragment.*
import com.google.android.material.tabs.TabLayout

class TeachHomeActivity : AppCompatActivity() {

    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout
    private lateinit var bar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teach_home)
        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)
        bar = findViewById(R.id.toolbar)

        // To make our toolbar show the application 
        // we need to give it to the ActionBar
        setSupportActionBar(bar)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(HomeTeachfrag(), "Home")
        adapter.addFragment(PublishTeachFrag(), "Published")
        adapter.addFragment(draftTeachFrag(), "Draft")
        adapter.addFragment(ArchivedTeachFrag(), "Archived")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)
    }




//    private fun setupViewPager(viewPager: ViewPager) {
//        val adapter = ViewPagerAdapter(this.supportFragmentManager)
//        adapter.addFragment(HomeTeachfrag(), "Home")
//        adapter.addFragment(PublishTeachFrag(), "Published")
//        adapter.addFragment(draftTeachFrag(), "Draft")
//        adapter.addFragment(ArchivedTeachFrag(), "Archived")
//
//        viewPager.adapter = adapter
//    }
//
//    internal class ViewPagerAdapter(manager: FragmentManager?) :
//        FragmentPagerAdapter(manager!!) {
//        private val mFragmentList: MutableList<Fragment> = ArrayList()
//        private val mFragmentTitleList: MutableList<String> = ArrayList()
//        override fun getItem(position: Int): Fragment {
//            return mFragmentList[position]
//        }
//
//        override fun getCount(): Int {
//            return mFragmentList.size
//        }
//
//        fun addFragment(fragment: Fragment, title: String) {
//            mFragmentList.add(fragment)
//            mFragmentTitleList.add(title)
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return mFragmentTitleList[position]
//        }
//
//    }
}