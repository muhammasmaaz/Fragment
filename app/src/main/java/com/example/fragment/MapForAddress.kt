package com.example.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class MapForAddress : AppCompatActivity(), OnMapReadyCallback {
    private var myMap :GoogleMap?=null
    private val FINE_PERMISSION_CODE=1
    private var currentlocation: Location?=null
    private var fusedlocationproviderclient: FusedLocationProviderClient?=null
    private var Addresstxtview:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_for_address)
        fusedlocationproviderclient=LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()


    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),FINE_PERMISSION_CODE)
            return
        }
        val task:Task<Location> = fusedlocationproviderclient?.getLastLocation() as Task<Location>
        task.addOnSuccessListener(OnSuccessListener {location ->

            if (location!=null)
            {
                currentlocation=location
                val mapfragment = getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
                mapfragment.getMapAsync(this)

            }
        })
    }

    override fun onMapReady(googlemap: GoogleMap?) {

        myMap=googlemap
        val latlng = LatLng(currentlocation!!.latitude,currentlocation!!.longitude)

        myMap?.addMarker(MarkerOptions().position(latlng).title(getAddress(latlng)))
        myMap?.moveCamera(CameraUpdateFactory.newLatLng(latlng))

        Addresstxtview=findViewById(R.id.Addresstxtview)
        Addresstxtview?.text=getAddress(latlng)
//        val fragment=RegisterFragment()
//        val address=Addresstxtview?.text.toString()
//        val bundle = Bundle()
//        bundle.putString("Addresstxtview",address)
//        fragment.arguments=bundle
//        supportFragmentManager.beginTransaction().replace(R.id.constraintlayout,fragment).commit()

//        activity ti activity send data
//        Addresstxtview?.text.toString()
//        val intent =Intent(this,MainActivity::class.java).also {
//            it.putExtra("location",Addresstxtview?.text.toString())
//            startActivity(it)
//        }

    }
    private fun getAddress(latlong: LatLng): String? {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(latlong.latitude,latlong.longitude,1)
        return list?.get(0)?.getAddressLine(0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==FINE_PERMISSION_CODE){
            if (grantResults.equals(0)  && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getLastLocation()
            }
            else{
                Toast.makeText(this,"Permission is required",Toast.LENGTH_LONG).show()
            }
        }
    }
}