package com.example.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.fragment.modelclass.ViewPagerAdopter
import com.google.android.material.bottomnavigation.BottomNavigationView

class Homebottomnavigationwithviewpager : AppCompatActivity() {

    private var viewPagerAdapter: ViewPagerAdopter?=null

    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        val pagerhomebottomnav: ViewPager
        var bottomnav: BottomNavigationView? = null


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homebottomnavigationwithviewpager)
//        val drawerLayout:DrawerLayout=findViewById(R.id.drawerLayout)
//
//        val navView:NavigationView=findViewById(R.id.navview)
//        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        navView.setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> Toast.makeText(this,"home",Toast.LENGTH_SHORT).show()
//
//                R.id.dashboard -> Toast.makeText(this,"dashboard",Toast.LENGTH_SHORT).show()
//
//                R.id.profile -> Toast.makeText(this,"profile",Toast.LENGTH_SHORT).show()
//                else -> {
//
//                }
//            }
//            true
//        }
//
        val adapter = ViewPagerAdopter(supportFragmentManager)
        pagerhomebottomnav = findViewById(R.id.pagerhomebottomnav)
        bottomnav = findViewById(R.id.bottomnavigationhome)

        pagerhomebottomnav.adapter = adapter
        bottomnav?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> pagerhomebottomnav.currentItem = 0

                R.id.home -> pagerhomebottomnav.currentItem = 1

                R.id.profile -> pagerhomebottomnav.currentItem = 2
                else -> {

                }
            }
            true


        }

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (toggle.onOptionsItemSelected(item)){
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//
//    }

}
