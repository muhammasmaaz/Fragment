package com.example.fragment


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



class MapForAddress : AppCompatActivity(), onDataPassedCommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_for_address)

        val mapfragment=MapFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mapcontainer,mapfragment).addToBackStack(null).commit()


    }

    override fun passdata(data: String) {
        val bundle=Bundle()
        bundle.putString("Location",data)

        val registerfragment=RegisterFragment()
        registerfragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.mapcontainer,registerfragment).commit()

    }

}