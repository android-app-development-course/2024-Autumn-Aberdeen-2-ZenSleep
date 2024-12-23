package com.example.sleepwell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class MusicFragment : Fragment() {

    private lateinit var songList: ArrayList<Song>
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music, container, false)

        listView = view.findViewById(R.id.music_list_view)

        songList = arrayListOf(
            Song("潮汐", "李爱恩", R.drawable.music1, R.raw.song1),
            Song("breeze", "松田光由", R.drawable.music2, R.raw.song2),
            Song("El éxtasis de", "Federico Durand", R.drawable.music3, R.raw.song3)
            // 添加更多歌曲
        )

        val songTitles = songList.map { it.title }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, songTitles)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedSong = songList[position]
            val intent = Intent(requireContext(), MusicPlayerActivity::class.java)
            intent.putExtra("currentSongIndex", position)
            intent.putExtra("songList", songList)
            startActivity(intent)
        }

        return view
    }
}
