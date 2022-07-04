package com.example.notekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.notekeeper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterSpinner = ArrayAdapter<CourseInfo>(this,
                            android.R.layout.simple_spinner_item,
                            DataManager.courses.values.toList())
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.content.spinnerCourses.adapter = adapterSpinner

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET){
            displayNote()
        } else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, POSITION_NOT_SET)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        binding.content.textNoteTitle.setText(note.title)
        binding.content.textNoteContent.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        binding.content.spinnerCourses.setSelection(coursePosition)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_next -> {
                MoveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun MoveNext(){
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (notePosition == DataManager.notes.lastIndex){
            val menuItem = menu.findItem(R.id.action_next)
            menuItem.icon = getDrawable(R.drawable.ic_baseline_block_24)
            menuItem.isEnabled = false
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = binding.content.textNoteTitle.text.toString()
        note.text = binding.content.textNoteContent.text.toString()
        note.course = binding.content.spinnerCourses.selectedItem as CourseInfo
    }

}