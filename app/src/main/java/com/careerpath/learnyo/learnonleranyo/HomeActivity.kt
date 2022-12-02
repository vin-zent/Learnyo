package com.careerpath.learnyo.learnonleranyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.fragments.FavFragment
import com.careerpath.learnyo.learnonleranyo.fragments.HomeFragment
import com.careerpath.learnyo.learnonleranyo.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottomNavMenu)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        loadFragment(HomeFragment())

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeMenu -> {
                val fragment = HomeFragment()
                loadFragment(fragment)

                return true
            }

            R.id.favMenu -> {
                val fragment = FavFragment()
                loadFragment(fragment)

                return true
            }
            R.id.profileMenu -> {
                val fragment = ProfileFragment()
                loadFragment(fragment)
                return true
            }
        }
        return false
    }
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}