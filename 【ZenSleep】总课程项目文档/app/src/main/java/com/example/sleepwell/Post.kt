package com.example.sleepwell

data class Post(
    val username: String,
    val content: String,
    val imageUris: List<String>,
    var likes: Int,
    var isLiked: Boolean,
    val comments: MutableList<String>
)

