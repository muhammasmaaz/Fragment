package com.example.fragment


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fragment.fragments.MapFragment
import com.example.fragment.fragments.RegisterFragment
import com.example.fragment.interfaces.onDataPassedCommunicator


class MapForAddress : AppCompatActivity(), onDataPassedCommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_for_address)

        val mapfragment= MapFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mapcontainer,mapfragment).addToBackStack(null).commit()


    }

    override fun passdata(data: String) {
        val bundle=Bundle()
        bundle.putString("Location",data)

        val registerfragment= RegisterFragment()
        registerfragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.mapcontainer,registerfragment).commit()

    }

}