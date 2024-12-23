package com.example.sleepwell

import java.io.Serializable

data class Song(val title: String, val artist: String, val albumArt: Int, val resId: Int) : Serializable
