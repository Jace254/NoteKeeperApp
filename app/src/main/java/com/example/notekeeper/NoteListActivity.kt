package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.notekeeper.databinding.ActivityNoteListBinding

class NoteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener{ view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }

        binding.contentNote.listNotes.adapter = ArrayAdapter<NoteInfo>(this,
                                                android.R.layout.simple_list_item_1,
                                                DataManager.notes)
        binding.contentNote.listNotes.setOnItemClickListener{parent,view, position, id ->
            val activityIntent = Intent(this, MainActivity::class.java)
            activityIntent.putExtra(NOTE_POSITION,position)
            startActivity(activityIntent)

        }
    }



    override fun onResume() {
        super.onResume()
        (binding.contentNote.listNotes.adapter as ArrayAdapter<*>).notifyDataSetChanged()
    }
}