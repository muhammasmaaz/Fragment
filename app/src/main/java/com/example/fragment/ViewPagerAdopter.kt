package com.example.fragment

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fragment.fragments.AudioPlayerFragment
import com.example.fragment.fragments.ContactFragment
import com.example.fragment.fragments.HomewithnavFragment

@SuppressLint("WrongConstant")
class ViewPagerAdopter(fragmentmanger:FragmentManager): FragmentPagerAdapter(fragmentmanger) {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> HomewithnavFragment()
            1 -> ContactFragment()
            else -> AudioPlayerFragment()
        }

    }
    override fun getCount(): Int {

        return 3
    }



}