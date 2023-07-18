package com.example.fragment.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.R
import com.example.fragment.adapter.contactadapter
import com.example.fragment.interfaces.OnCallListener
import com.example.fragment.modelclass.contactmodel
import com.example.fragment.`object`.Utility


@RequiresApi(Build.VERSION_CODES.M)
class ContactFragment : Fragment(), OnCallListener<contactmodel> {

    private var contactlist:RecyclerView? = null
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_contact, container, false)

        contactlist=view.findViewById(R.id.contactlist)


        loadContacts()


        return view
    }

    override fun onCall(contactmodel: contactmodel) {
        Utility.makeCall(requireContext(), contactmodel.number)
    }
    private fun loadContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(requireContext(),
                Manifest.permission.READ_CONTACTS) != PermissionChecker.PERMISSION_GRANTED
            ||checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE) != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {

            contactlist?.setHasFixedSize(true)
            contactlist?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = contactadapter(getContacts())
            contactlist?.adapter = adapter

            adapter.setListener(this)


        }
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                showToast("Permission must be granted in order to display contacts information")
            }
        }
    }

    @SuppressLint("Range", "SuspiciousIndentation")
    private fun getContacts(): ArrayList<contactmodel> {
        val contacts = ArrayList<contactmodel>()
        val cursor = activity?.contentResolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

            if (cursor!!.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val namephone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()
                    val photophone = (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)))

                    if (phoneNumber > 0) {
                        val cursorPhone = activity?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone?.count!! > 0) {
                            while (cursorPhone.moveToNext() == true) {
                                val numberphone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                contacts.add(contactmodel(namephone, numberphone,if(photophone!=null) photophone else ""))


                            }





                        }
                        cursorPhone.close()
                    }
                }
            } else {
                showToast("No contacts available!")
            }
            cursor.close()


        return contacts
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


}