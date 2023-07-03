package com.example.fragment


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isVisible
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {


    private var btnhomelogin: Button? =null
    private var btnHomeregister: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

    }
    private fun initUI() {
        this.btnhomelogin = findViewById<Button>(R.id.btnHomeLogin)
        this.btnHomeregister = findViewById<Button>(R.id.btnHomeRegister)
        clickListener()

    }
    private fun clickListener() {

        this.btnhomelogin?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container,LoginFragment()).commit()
            btnhomelogin?.isVisible=false
            btnHomeregister?.isVisible=false

        }
        this.btnHomeregister?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container,RegisterFragment()).commit()
            btnhomelogin?.isVisible=false
            btnHomeregister?.isVisible=false
        }
    }


}


