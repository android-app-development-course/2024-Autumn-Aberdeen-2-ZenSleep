package com.example.sleepwell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private lateinit var notesDatabaseHelper: NotesDatabaseHelper
    private lateinit var notesListView: ListView
    private lateinit var addNoteButton: Button
    private lateinit var adapter: ArrayAdapter<String>
    private var notesList: MutableList<Note> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesDatabaseHelper = NotesDatabaseHelper(requireContext())
        notesListView = view.findViewById(R.id.notes_list_view)
        addNoteButton = view.findViewById(R.id.button_add_note)

        addNoteButton.setOnClickListener {
            val intent = Intent(requireContext(), EditNoteActivity::class.java)
            startActivity(intent)
        }

        notesListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val note = notesList[position]
            notesDatabaseHelper.deleteNoteById(note.id)
            Toast.makeText(requireContext(), "记录已删除", Toast.LENGTH_SHORT).show()
            loadNotes()
        }

        loadNotes()
    }

    private fun loadNotes() {
        notesList = notesDatabaseHelper.getAllNotes().toMutableList()
        val notesContentList = notesList.map { "${it.content}\n${it.timestamp}" }
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notesContentList)
        notesListView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }
}
