package com.example.sleepwell

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(private val context: Context, private val posts: MutableList<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.username_text)
        val contentTextView: TextView = itemView.findViewById(R.id.post_content)
        val imagesRecyclerView: RecyclerView = itemView.findViewById(R.id.images_recycler_view)
        val likeButton: ImageButton = itemView.findViewById(R.id.like_button)
        val likeCountTextView: TextView = itemView.findViewById(R.id.like_count)
        val commentButton: ImageButton = itemView.findViewById(R.id.comment_button)
        val commentInput: EditText = itemView.findViewById(R.id.comment_input)
        val commentsContainer: LinearLayout = itemView.findViewById(R.id.comments_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        holder.usernameTextView.text = post.username
        holder.contentTextView.text = post.content
        holder.likeCountTextView.text = post.likes.toString()
        holder.likeButton.setImageResource(if (post.isLiked) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)

        holder.imagesRecyclerView.visibility = if (post.imageUris.isNotEmpty()) View.VISIBLE else View.GONE
        if (post.imageUris.isNotEmpty()) {
            holder.imagesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            holder.imagesRecyclerView.adapter = ImagesAdapter(context, post.imageUris) { imageUri ->
            }
        }

        holder.likeButton.setOnClickListener {
            post.isLiked = !post.isLiked
            post.likes += if (post.isLiked) 1 else -1
            holder.likeCountTextView.text = post.likes.toString()
            holder.likeButton.setImageResource(if (post.isLiked) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
        }

        holder.commentButton.setOnClickListener {
            holder.commentInput.visibility = if (holder.commentInput.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        holder.commentInput.setOnEditorActionListener { _, _, _ ->
            val comment = holder.commentInput.text.toString()
            if (comment.isNotEmpty()) {
                post.comments.add(comment)
                holder.commentInput.text.clear()
                holder.commentInput.visibility = View.GONE
                updateComments(holder.commentsContainer, post.comments)
                notifyDataSetChanged()
            }
            true
        }

        updateComments(holder.commentsContainer, post.comments)
    }

    private fun updateComments(container: LinearLayout, comments: List<String>) {
        container.visibility = if (comments.isNotEmpty()) View.VISIBLE else View.GONE
        container.removeAllViews()
        comments.forEach { comment ->
            val commentTextView = TextView(context)
            commentTextView.text = comment
            commentTextView.setPadding(0, 8, 0, 8)
            container.addView(commentTextView)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun addPost(post: Post) {
        posts.add(0, post)
        notifyItemInserted(0)
    }
}
