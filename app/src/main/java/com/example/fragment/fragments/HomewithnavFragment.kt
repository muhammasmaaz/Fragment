package com.example.fragment.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fragment.R
import com.example.fragment.interfaces.weatherapiinterface
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
class HomewithnavFragment : Fragment() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_homewithnav, container, false)

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

        fetchweatherdata()
        return view
    }

    private fun fetchweatherdata() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(weatherapiinterface::class.java)
        val response = retrofit.getWeatherData("Lahore", "907d232eaa93fcce054f3599021123df" , "metric")


        response.enqueue(object : Callback<Weatherapi> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Weatherapi>, response: Response<Weatherapi>) {

                val responseBody = response.body()!!

                temp!!.text = ((responseBody.list[0].main.temp).toInt()).toString()+"°C"
                tempmax!!.text = "Max Temp: "+responseBody.list[0].main.temp_max.toString()
                tempmin!!.text = "Mini Temp: "+responseBody.list[0].main.temp_min.toString()
                address!!.text = responseBody.city.name

                val simpleDateFormat = SimpleDateFormat("hh:mm a")
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
                feelike!!.text = responseBody.list[0].weather[0].main



            }

            override fun onFailure(call: Call<Weatherapi>, t: Throwable) {
                Log.d("DATA", t.toString())
            }

        })



    }

}