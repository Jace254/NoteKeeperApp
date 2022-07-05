package com.example.notekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.notekeeper.DataManager.notes
import com.example.notekeeper.databinding.ActivityMainBinding


class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var notePosition = POSITION_NOT_SET
    private lateinit var spinnerCourses: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinnerCourses = binding.content.spinnerCourses
        val adapterSpinner = ArrayAdapter<CourseInfo>(this,
                            android.R.layout.simple_spinner_item,
                            DataManager.courses.values.toList())
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCourses.adapter = adapterSpinner

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
        //logic for whether a note position is set or not.If it is not set
        //the note is created and added to the data manager class
        if(notePosition != POSITION_NOT_SET){
            displayNote()
        } else {
            notes.add(NoteInfo())
            notePosition = notes.lastIndex
        }

    }

    //saves the instance of MainActivity when it is destroyed and returns it when it is created
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    //displays a selected note according to it's id in the notes array list
    private fun displayNote() {
        val note = notes[notePosition]
        binding.content.textNoteTitle.setText(note.title)
        binding.content.textNoteContent.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_next -> {
                moveNext()
                true
            }
            R.id.action_prev -> {
                movePrev()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun movePrev() {
        --notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    //Moves to the next note in the app
    private fun moveNext(){
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (notePosition == notes.lastIndex){
            val menuItem = menu.findItem(R.id.action_next)
            with(menuItem){
                icon = getDrawable(R.drawable.ic_baseline_block_24)
                isEnabled = false
            }
        }
        if (notePosition == 0) {
            val menuItem = menu.findItem(R.id.action_prev)
            with(menuItem){
                icon = getDrawable(R.drawable.ic_baseline_block_24)
                isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    //when the MainActivity is paused and is no longer visible
    override fun onPause() {
        super.onPause()
        saveNote()
    }

    //saves a note for persistence purposes within the app
    private fun saveNote() {
        val note = notes[notePosition]
        with(note){
            title = binding.content.textNoteTitle.text.toString()
            text = binding.content.textNoteContent.text.toString()
            course = spinnerCourses.selectedItem as CourseInfo
        }
    }

}