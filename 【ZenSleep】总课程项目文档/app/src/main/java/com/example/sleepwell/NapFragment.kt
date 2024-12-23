package com.example.sleepwell

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NapFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddPost: ImageButton
    private lateinit var postAdapter: PostAdapter

    private val samplePosts = listOf(
        Post("好蓝", "早睡打卡+1", emptyList(), 0, false, mutableListOf()),
        Post("睡到起飞", "#烟火#睡梦中的星空会是这个样子吗?", emptyList(), 0, false, mutableListOf()),
        Post("爱睡觉的小飞", "ZenSleep的设计太棒了！", emptyList(), 0, false, mutableListOf()),
        Post("咩咩咩", "今晚一定早睡！", emptyList(), 0, false, mutableListOf()),
        Post("momo", "晚安啦家人们，明天又是美好的一天！", emptyList(), 0, false, mutableListOf())
    )

    companion object {
        private const val REQUEST_CODE_NEW_POST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        fabAddPost = view.findViewById(R.id.fab_add_post)

        postAdapter = PostAdapter(requireContext(), samplePosts.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = postAdapter

        fabAddPost.setOnClickListener {
            val intent = Intent(requireContext(), EditPostActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NEW_POST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_NEW_POST && resultCode == Activity.RESULT_OK) {
            val username = data?.getStringExtra("username") ?: "User"
            val content = data?.getStringExtra("content") ?: ""
            val imageUris = data?.getStringArrayListExtra("imageUris")?.map { it.toString() } ?: emptyList()
            val post = Post(username, content, imageUris, 0, false, mutableListOf())
            postAdapter.addPost(post)
        }
    }
}
