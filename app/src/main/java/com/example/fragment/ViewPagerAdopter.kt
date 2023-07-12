package com.example.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fragment.fragments.ContactFragment
import com.example.fragment.fragments.HomewithnavFragment
import com.example.fragment.fragments.profileFragment

@SuppressLint("WrongConstant")
class ViewPagerAdopter(fragmentmanger:FragmentManager): FragmentPagerAdapter(fragmentmanger) {
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> HomewithnavFragment()
            1 -> ContactFragment()
            else -> profileFragment()
        }

    }
    override fun getCount(): Int {

        return 3
    }



}