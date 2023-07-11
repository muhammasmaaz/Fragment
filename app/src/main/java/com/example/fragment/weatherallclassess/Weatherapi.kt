package com.example.fragment.weatherallclassess

data class Weatherapi(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DT>,
    val message: Int
)