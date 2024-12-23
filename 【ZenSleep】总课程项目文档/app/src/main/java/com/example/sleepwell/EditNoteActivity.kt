package com.example.sleepwell

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditNoteActivity : AppCompatActivity() {

    private lateinit var notesDatabaseHelper: NotesDatabaseHelper
    private lateinit var textViewDate: TextView
    private lateinit var editTextNoteContent: EditText
    private lateinit var buttonCancel: Button
    private lateinit var buttonDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        notesDatabaseHelper = NotesDatabaseHelper(this)
        textViewDate = findViewById(R.id.text_view_date)
        editTextNoteContent = findViewById(R.id.edit_text_note_content)
        buttonCancel = findViewById(R.id.button_cancel)
        buttonDone = findViewById(R.id.button_done)

        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        textViewDate.text = currentDate

        buttonCancel.setOnClickListener {
            finish()
        }

        buttonDone.setOnClickListener {
            val content = editTextNoteContent.text.toString()
            if (content.isNotEmpty()) {
                notesDatabaseHelper.addNote(content, currentDate)
                Toast.makeText(this, "记录已添加", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
