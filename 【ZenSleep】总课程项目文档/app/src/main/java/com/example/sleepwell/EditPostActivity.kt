package com.example.sleepwell

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class EditPostActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var imageContainer: LinearLayout
    private lateinit var buttonSelectImage: Button
    private lateinit var buttonTakePhoto: Button
    private lateinit var buttonPost: Button

    private val imageUris: MutableList<Uri> = mutableListOf()

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        editText = findViewById(R.id.edit_text)
        imageContainer = findViewById(R.id.image_container)
        buttonSelectImage = findViewById(R.id.button_select_image)
        buttonTakePhoto = findViewById(R.id.button_take_photo)
        buttonPost = findViewById(R.id.button_post)

        buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        buttonTakePhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

        buttonPost.setOnClickListener {
            val content = editText.text.toString()
            val resultIntent = Intent().apply {
                putExtra("username", "User") // Replace with actual username
                putExtra("content", content)
                putStringArrayListExtra("imageUris", ArrayList(imageUris.map { it.toString() }))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    data?.clipData?.let { clipData ->
                        for (i in 0 until clipData.itemCount) {
                            val imageUri = clipData.getItemAt(i).uri
                            imageUris.add(imageUri)
                            addImageToContainer(imageUri)
                        }
                    } ?: data?.data?.let { imageUri ->
                        imageUris.add(imageUri)
                        addImageToContainer(imageUri)
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    val imageUri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, bitmap, null, null))
                    imageUris.add(imageUri)
                    addImageToContainer(imageUri)
                }
            }
        }
    }

    private fun addImageToContainer(imageUri: Uri) {
        val imageView = ImageView(this).apply {
            setImageURI(imageUri)
            layoutParams = LinearLayout.LayoutParams(200, 200).apply {
                setMargins(8, 8, 8, 8)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        imageContainer.addView(imageView)
    }
}
