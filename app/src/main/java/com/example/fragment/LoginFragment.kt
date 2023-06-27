package com.example.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var username: EditText ?=null
    private var passward: EditText ?=null
    private var loginbtn: Button ?=null
    private var view: View?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false)

        initUi()
        onclicklistner()

        return view
    }

    private fun initUi() {

        username = view?.findViewById<EditText>(R.id.Username)
        passward = view?.findViewById<EditText>(R.id.Passward)
        loginbtn = view?.findViewById<Button>(R.id.FragbtnLogin)

    }

    //onclicklistner for data show in edittext to textview
    @SuppressLint("SetTextI18n")
    private fun onclicklistner() {
        loginbtn?.setOnClickListener{
            val emptyusername=username?.text.toString()
            val emptypassward=passward?.text.toString()
            if (TextUtils.isEmpty(emptyusername)){
                error("Username is empty")
            }
            else if (TextUtils.isEmpty(emptypassward)){
                error("Password is empty")
            }
            else {
                error("Success")
            }
        }
    }
}