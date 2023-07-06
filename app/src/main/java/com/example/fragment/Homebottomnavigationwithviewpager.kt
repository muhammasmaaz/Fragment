package com.example.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.fragment.fragments.DashboardFragment
import com.example.fragment.fragments.HomewithnavFragment
import com.example.fragment.fragments.profileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class Homebottomnavigationwithviewpager : AppCompatActivity() {

    private var viewPagerAdapter: ViewPagerAdopter?=null

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
//        val pagerhomebottomnav: ViewPager
//        var bottomnav: BottomNavigationView? = null


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homebottomnavigationwithviewpager)
        val drawerLayout:DrawerLayout=findViewById(R.id.drawerLayout)

        val navView:NavigationView=findViewById(R.id.navview)
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> Toast.makeText(this,"home",Toast.LENGTH_SHORT).show()

                R.id.dashboard -> Toast.makeText(this,"dashboard",Toast.LENGTH_SHORT).show()

                R.id.profile -> Toast.makeText(this,"profile",Toast.LENGTH_SHORT).show()
                else -> {

                }
            }
            true
        }

        val adapter = ViewPagerAdopter(supportFragmentManager)
//        pagerhomebottomnav = findViewById(R.id.pagerhomebottomnav)
//        bottomnav = findViewById(R.id.bottomnavigationhome)

//        pagerhomebottomnav.adapter = adapter
//        bottomnav?.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> pagerhomebottomnav.currentItem = 0
//
//                R.id.dashboard -> pagerhomebottomnav.currentItem = 1
//
//                R.id.profile -> pagerhomebottomnav.currentItem = 2
//                else -> {
//
//                }
//            }
//            true
//
//
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)

    }

}