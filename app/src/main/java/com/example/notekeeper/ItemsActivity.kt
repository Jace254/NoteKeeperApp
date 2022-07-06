package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notekeeper.databinding.ActivityItemsBinding

class ItemsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityItemsBinding
    private val listItems by lazy{
        binding.appBarItems.content.listItems
    }
    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    private val noteRecyclerAdapter by lazy {
        NoteRecyclerAdapter(this, DataManager.notes)
    }
    private val courseLayoutManager by lazy {
        GridLayoutManager(this,2)
    }
    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        setSupportActionBar(binding.appBarItems.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        val toggle = ActionBarDrawerToggle (
            this, drawerLayout,binding.appBarItems.toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.appBarItems.fab.setOnClickListener {
            startActivity(Intent(this,NoteActivity::class.java))
        }

        displayNotes()

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_notes -> {
                    displayNotes()
                }
                R.id.nav_courses -> {
                    displayCourses()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_courses, R.id.nav_notes
            ), drawerLayout
        )

    }

    private fun displayNotes() {
        listItems.layoutManager = noteLayoutManager
        listItems.adapter = noteRecyclerAdapter

        binding.navView.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        listItems.layoutManager = courseLayoutManager
        listItems.adapter = courseRecyclerAdapter

        binding.navView.menu.findItem(R.id.nav_courses).isChecked = true
    }




    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)//close drawer
        } else {
            super.onBackPressed()//close app
        }
    }

    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }
}