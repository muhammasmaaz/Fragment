package com.example.fragment.fragments

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.RECEIVER_VISIBLE_TO_INSTANT_APPS
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fragment.R
import com.example.fragment.interfaces.weatherapiinterface
import com.example.fragment.modelclass.ConnectivityReceiver
import com.example.fragment.weatherallclassess.Weatherapi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val BASE_URL="https://api.openweathermap.org/data/2.5/"
class HomewithnavFragment : Fragment() , ConnectivityReceiver.ConnectivityReceiverListener {
    //https://api.openweathermap.org/data/2.5/forecast?q=Lahore&appid=907d232eaa93fcce054f3599021123df&units=metric

    var temp: TextView? = null
    var tempmax:TextView?=null
    var tempmin:TextView?=null
    var address:TextView?=null
    var updateat:TextView?=null
    var sunrise: TextView? = null
    var sunset: TextView? = null
    var wind: TextView? = null
    var pressure: TextView? = null
    var humidity: TextView? = null
    var feelike: TextView? = null
    var swiperefreshlatout: SwipeRefreshLayout? = null
    var citytemp: EditText? = null
    var searchcity: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_weather, container, false)

        temp = view.findViewById(R.id.temp)
        tempmax = view.findViewById(R.id.temp_max)
        tempmin = view.findViewById(R.id.temp_min)
        address= view.findViewById(R.id.address)
        updateat= view.findViewById(R.id.updated_at)
        sunrise= view.findViewById(R.id.sunrise)
        sunset= view.findViewById(R.id.sunset)
        wind= view.findViewById(R.id.wind)
        pressure= view.findViewById(R.id.pressure)
        humidity= view.findViewById(R.id.humidity)
        feelike= view.findViewById(R.id.status)
        swiperefreshlatout= view.findViewById(R.id.swiperefreshlayout)
        citytemp= view.findViewById(R.id.cityTemp)
        searchcity= view.findViewById(R.id.searchcity)


        registerReceiver(requireContext(),ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION),RECEIVER_VISIBLE_TO_INSTANT_APPS)
        searchcity?.setOnClickListener {
            fetchweatherdata()

        }
        swiperefreshlatout?.setOnRefreshListener {
            fetchweatherdata()
            swiperefreshlatout?.isRefreshing=false
        }

        return view
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            Toast.makeText(requireContext(),"you are offline", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(),"you are online", Toast.LENGTH_LONG).show()
        }
    }
    private fun fetchweatherdata() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(weatherapiinterface::class.java)

        val response = retrofit.getWeatherData(citytemp?.text.toString(), "907d232eaa93fcce054f3599021123df" , "metric")




        response.enqueue(object : Callback<Weatherapi> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Weatherapi>, response: Response<Weatherapi>) {

                val responseBody = response.body()!!

                temp!!.text = ((responseBody.list[0].main.temp).toInt()).toString()+"°C"
                tempmax!!.text = "Max Temp: "+responseBody.list[0].main.temp_max.toString()
                tempmin!!.text = "Mini Temp: "+responseBody.list[0].main.temp_min.toString()
                address!!.text = responseBody.city.name

                val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val dateandtime = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
                val currentDate = dateandtime.format(Date())
                updateat!!.text=currentDate
                val sunrisetime = simpleDateFormat.format(responseBody.city.sunrise.toLong())
                sunrise!!.text = sunrisetime
                val sunsettime = simpleDateFormat.format(responseBody.city.sunset.toLong())
                sunset!!.text = sunsettime
                wind!!.text = responseBody.list[0].wind.speed.toString()+" mi/h"
                pressure!!.text = responseBody.list[0].main.pressure.toString()+" hpa"
                humidity!!.text = responseBody.list[0].main.humidity.toString()+" %"
                feelike!!.text = responseBody.list[0].weather[0].description



            }

            override fun onFailure(call: Call<Weatherapi>, t: Throwable) {
                Log.d("DATA", t.toString())
            }

        })



    }

}