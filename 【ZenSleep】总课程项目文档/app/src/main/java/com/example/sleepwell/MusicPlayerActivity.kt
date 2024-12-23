package com.example.sleepwell

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playPauseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var seekBar: SeekBar
    private var isPlaying = false
    private var currentSongIndex = 0
    private lateinit var songList: ArrayList<Song>
    private lateinit var titleTextView: TextView
    private lateinit var artistTextView: TextView
    private lateinit var albumArtImageView: ImageView
    private var updateSeekBarThread: Thread? = null
    @Volatile
    private var isThreadRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
        songList = intent.getSerializableExtra("songList") as ArrayList<Song>
        currentSongIndex = intent.getIntExtra("currentSongIndex", 0)
        titleTextView = findViewById(R.id.music_title)
        artistTextView = findViewById(R.id.music_artist)
        albumArtImageView = findViewById(R.id.album_art)
        playPauseButton = findViewById(R.id.button_play_pause)
        nextButton = findViewById(R.id.button_next)
        previousButton = findViewById(R.id.button_previous)
        seekBar = findViewById(R.id.music_seekbar)
        mediaPlayer = MediaPlayer()
        // 初始播放第一首歌
        playSong(currentSongIndex)
        playPauseButton.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                playPauseButton.text = "播放"
            } else {
                mediaPlayer.start()
                playPauseButton.text = "暂停"
            }
            isPlaying = !isPlaying
        }
        nextButton.setOnClickListener {
            currentSongIndex = (currentSongIndex + 1) % songList.size
            playSong(currentSongIndex)
        }
        previousButton.setOnClickListener {
            currentSongIndex = if (currentSongIndex - 1 < 0) songList.size - 1 else currentSongIndex - 1
            playSong(currentSongIndex)
        }
        mediaPlayer.setOnCompletionListener {
            nextButton.performClick()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        updateSeekBarThread = Thread {
            while (isThreadRunning) {
                try {
                    if (mediaPlayer.isPlaying) {
                        seekBar.progress = mediaPlayer.currentPosition
                    }
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        updateSeekBarThread?.start()
    }

    private fun playSong(index: Int) {
        val selectedSong = songList[index]

        titleTextView.text = selectedSong.title
        artistTextView.text = selectedSong.artist
        albumArtImageView.setImageResource(selectedSong.albumArt)

        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(this, selectedSong.resId)
        mediaPlayer.setOnPreparedListener {
            seekBar.max = mediaPlayer.duration
            mediaPlayer.start()
            isPlaying = true
            playPauseButton.text = "暂停"
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        isThreadRunning = false
        updateSeekBarThread?.interrupt()
        mediaPlayer.release()
    }
}
