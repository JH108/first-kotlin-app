package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_note_list.*

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.buttonGotoAddNote).setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }

        listItems.layoutManager = LinearLayoutManager(this)
        listItems.adapter = NoteRecyclerAdapter(this, DataManager.notes)
    }

    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }
}