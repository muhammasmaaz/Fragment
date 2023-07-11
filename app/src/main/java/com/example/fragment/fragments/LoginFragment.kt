package com.example.fragment.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fragment.Homebottomnavigationwithviewpager
import com.example.fragment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var username: EditText ?=null
    private var passward: EditText ?=null
    private var loginbtn: Button ?=null
    private var view: View?=null
    private var firebasedatabase:FirebaseDatabase?=null
    private var dbref:DatabaseReference?=null
    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false)

        firebasedatabase=FirebaseDatabase.getInstance()
        dbref=firebasedatabase?.getReference("User Data")
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
                Toast.makeText(requireContext(),"username empty",Toast.LENGTH_LONG).show()
            }
            else if (TextUtils.isEmpty(emptypassward)){

                Toast.makeText(requireContext(),"password empty",Toast.LENGTH_LONG).show()
            }
            else {

                Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                auth.signInWithEmailAndPassword(emptyusername, emptypassward)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(requireContext(),"Login success",Toast.LENGTH_LONG).show()
                            val intent = Intent(activity,Homebottomnavigationwithviewpager::class.java)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }
    }
}