package com.example.fragment.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.fragment.R
import com.example.fragment.interfaces.onDataPassedCommunicator
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


class MapFragment : Fragment(), OnMapReadyCallback {
    private var myMap : GoogleMap?=null
    private val FINE_PERMISSION_CODE=1
    private var currentlocation: Location?=null
    private var fusedlocationproviderclient: FusedLocationProviderClient?=null
    private var Addresstxtview: TextView?=null
    private var savelocation: Button?=null
    private lateinit var onDataPassedCommunicator: onDataPassedCommunicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedlocationproviderclient= LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_map, container, false)

        onDataPassedCommunicator=requireActivity() as onDataPassedCommunicator
        savelocation=view?.findViewById(R.id.saveLocation)
        savelocation?.setOnClickListener {

            onDataPassedCommunicator.passdata("Location")
        }
        return view
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),FINE_PERMISSION_CODE)
            return
        }
        val task: Task<Location> = fusedlocationproviderclient?.getLastLocation() as Task<Location>
        task.addOnSuccessListener(OnSuccessListener { location ->

            if (location!=null)
            {
                currentlocation=location
                val mapfragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapfragment.getMapAsync(this)


            }
        })
    }

    override fun onMapReady(googlemap: GoogleMap) {

        myMap=googlemap
        val latlng = LatLng(currentlocation!!.latitude,currentlocation!!.longitude)

        myMap?.addMarker(MarkerOptions().position(latlng).title(getAddress(latlng)))
        myMap?.moveCamera(CameraUpdateFactory.newLatLng(latlng))

        Addresstxtview=view?.findViewById(R.id.Addresstxtview)
        Addresstxtview?.text=getAddress(latlng)
        savelocation?.setOnClickListener {

            onDataPassedCommunicator.passdata(Addresstxtview?.text.toString())
        }
    }
    private fun getAddress(latlong: LatLng): String? {
        val geocoder = Geocoder(requireContext())
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
                Toast.makeText(requireContext(),"Permission is required", Toast.LENGTH_LONG).show()
            }
        }
    }

}