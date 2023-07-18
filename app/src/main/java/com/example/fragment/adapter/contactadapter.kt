package com.example.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.R
import com.example.fragment.interfaces.OnCallListener
import com.example.fragment.modelclass.contactmodel
import com.squareup.picasso.Picasso

class contactadapter(private val contactList: ArrayList<contactmodel>) :
    RecyclerView.Adapter<contactadapter.ViewHolder>() {
    private var onCallListener: OnCallListener<contactmodel>? = null

    fun setListener(onCallListener: OnCallListener<contactmodel>) {
        this.onCallListener = onCallListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contactadapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contacts, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactmodel = contactList[position]
        holder.bindItems(contactmodel)
        holder.itemView.findViewById<ImageButton>(R.id.callButton).setOnClickListener {
            if (onCallListener != null) {
                onCallListener!!.onCall(contactmodel)
            }
        }

    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(contactmodel: contactmodel) {
            val tvName = itemView.findViewById<TextView>(R.id.name_phone)
            val tvNumber = itemView.findViewById<TextView>(R.id.number_phone)
            val tvphoto = itemView.findViewById<ImageView>(R.id.img_phone)


            tvName.text = contactmodel.name
            tvNumber.text = contactmodel.number
            if (contactmodel.photo.toString().length> 0) {
                Picasso.get().load(contactmodel.photo?:"").placeholder(R.drawable.baseline_perm_contact_calendar_24).error(R.drawable.baseline_perm_contact_calendar_24).into(tvphoto)

            }
            else{
                Picasso.get().load(R.drawable.baseline_perm_contact_calendar_24).placeholder(R.drawable.baseline_perm_contact_calendar_24).error(R.drawable.baseline_perm_contact_calendar_24).into(tvphoto)

            }

        }
    }
}