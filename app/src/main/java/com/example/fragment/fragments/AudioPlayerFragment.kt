package com.example.fragment.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.MusicPlayerServices.MusicPlayerService
import com.example.fragment.R
import com.example.fragment.adapter.audioadapter
import com.example.fragment.databinding.FragmentAudioplayerBinding
import com.example.fragment.interfaces.AudioInterface
import com.example.fragment.modelclass.audiodataclass
import com.example.fragment.`object`.Constant


class AudioPlayerFragment : Fragment(), AudioInterface {


    lateinit var binding: FragmentAudioplayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var audiorecyclerview: RecyclerView
    private lateinit var audioarraylist: ArrayList<audiodataclass>
    lateinit var imgid: Array<Int>
    lateinit var audioname: Array<String>
    lateinit var audiomp3: Array<Int>

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAudioplayerBinding.inflate(layoutInflater)
        imgid = arrayOf(
            R.drawable.baseline_audio_file_24,
            R.drawable.baseline_audio_file_24,
            R.drawable.baseline_audio_file_24,
            R.drawable.baseline_audio_file_24,
            R.drawable.baseline_audio_file_24,
            R.drawable.baseline_audio_file_24,
            R.drawable.baseline_audio_file_24,
            R.drawable.baseline_audio_file_24
        )
        audioname = arrayOf(
            "bird",
            "water",
            "forest",
            "jungle",
            "tree",
            "rain",
            "thunder",
            "leaves"
        )
        audiomp3 = arrayOf(
            R.raw.bird,
            R.raw.water,
            R.raw.forest,
            R.raw.jungle,
            R.raw.tree,
            R.raw.rain,
            R.raw.thunder,
            R.raw.leaves
        )

//        audiorecyclerview=view.findViewById(R.id.audiorecyclerview)
        binding.audiorecyclerview.layoutManager = LinearLayoutManager(requireContext())
        audiorecyclerview.setHasFixedSize(true)
        audioarraylist = arrayListOf<audiodataclass>()
        getAudioData()
        return binding.root
    }


    private fun getAudioData() {
        for (i in imgid.indices) {
            val audio = audiodataclass(audioname[i], imgid[i], audiomp3[i])
            audioarraylist.add(audio)
        }
        audiorecyclerview.adapter = audioadapter(requireContext(), audioarraylist, this)
    }

    override fun playaudio(position: Int) {
        try {


            val serviceIntent = Intent(requireContext(), MusicPlayerService::class.java)
            serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android")
            serviceIntent.putExtra("inputExtratitle", audioarraylist[position].audioname)
            serviceIntent.putExtra("inputExtraaudiomp3", audioarraylist[position].audiomp3)
            serviceIntent.action = Constant.ACTION.STARTFOREGROUND_ACTION
            serviceIntent.action = Constant.ACTION.STOPFOREGROUND_ACTION
            Toast.makeText(requireContext(), audioname[position], Toast.LENGTH_SHORT).show()


            ContextCompat.startForegroundService(requireContext(), serviceIntent)
//            if (mediaPlayer!=null&&!mediaPlayer!!.isPlaying){
//                mediaPlayer = MediaPlayer.create(context, audioarraylist[position].audiomp3)
//                mediaPlayer?.start()
//                val intent = Intent(requireContext(),MusicPlayerService::class.java)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    requireActivity().startService(intent)
//                }
//
//
//            }


//            else{
//                mediaPlayer?.stop()
//                mediaPlayer?.release()
//                mediaPlayer = MediaPlayer.create(context, audioarraylist[position].audiomp3)
//                mediaPlayer?.start()
//
//
//
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun pauseaudio(position: Int) {

        val serviceIntent = Intent(requireContext(), MusicPlayerService::class.java)
        requireContext().stopService(serviceIntent)
        serviceIntent.action = Constant.ACTION.STOPFOREGROUND_ACTION
        mediaPlayer?.pause()
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null

        Toast.makeText(context, "here", Toast.LENGTH_SHORT).show()
    }

}