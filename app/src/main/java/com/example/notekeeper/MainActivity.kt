package com.example.notekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.notekeeper.databinding.ActivityMainBinding
import com.example.notekeeper.databinding.ContentMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var notePosition = POSITION_NOT_SET
    private var  contentBinding: ContentMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contentBinding = binding.content

        val adapterSpinner = ArrayAdapter<CourseInfo>(this,
                            android.R.layout.simple_spinner_item,
                            DataManager.courses.values.toList())
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        contentBinding?.spinnerCourses?.adapter = adapterSpinner

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, ) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
        //logic for whether a note position is set or not.If it is not set
        //the note is created and added to the data manager class
        if(notePosition != POSITION_NOT_SET){
            displayNote()
        } else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }

    }

    //saves the instance of MainActivity when it is destroyed and returns it when it is created
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    //displays a selected note according to it's id in the notes array list
    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        contentBinding?.textNoteTitle?.setText(note.title)
        contentBinding?.textNoteContent?.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        contentBinding?.spinnerCourses?.setSelection(coursePosition)

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

    //Moves to the next note in the app
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

    //when the MainActivity is paused and is no longer visible
    override fun onPause() {
        super.onPause()
        saveNote()
    }

    //saves a note for persistence purposes within the app
    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        with(note){
            title = contentBinding?.textNoteTitle?.text.toString()
            text = contentBinding?.textNoteContent?.text.toString()
            course = contentBinding?.spinnerCourses?.selectedItem as CourseInfo
        }
    }

}