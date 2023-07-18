package com.example.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.R
import com.example.fragment.interfaces.AudioInterface
import com.example.fragment.modelclass.audiodataclass

class audioadapter(val context: Context, private val audioarraylist: ArrayList<audiodataclass>, val audiointerface:AudioInterface) :
    RecyclerView.Adapter<audioadapter.myViewHolder>() {

    var positioncurrent:Int=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.audioplayitem, parent, false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: audioadapter.myViewHolder, position: Int) {

        val currentitem = audioarraylist[position]
        holder.audiotv.text = currentitem.audioname
        holder.audioimg.setImageResource(currentitem.audioimage)





        holder.playbtn.setOnClickListener {

            audiointerface.playaudio(position)

            positioncurrent=position

        }


        holder.pausebtn.setOnClickListener {

            if(positioncurrent==position) {
                audiointerface.pauseaudio(position)
                positioncurrent=-1

            }



        }


    }

    override fun getItemCount(): Int {
        return audioarraylist.size
    }

    class myViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val audiotv = view.findViewById<TextView>(R.id.audiotv)
        val audioimg = view.findViewById<ImageView>(R.id.audioimg)
        val playbtn = view.findViewById<ImageButton>(R.id.playbtn)
        val pausebtn = view.findViewById<ImageButton>(R.id.pausebtn)

    }
}