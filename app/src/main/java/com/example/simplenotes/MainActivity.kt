package com.example.simplenotes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.view.Gravity


class MainActivity : AppCompatActivity() {

    // populate menu with menu_main.xml
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // close application on back
    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this, "Changes discarded", Toast.LENGTH_SHORT).show()
        this.finish()
        return super.onSupportNavigateUp()
    }

    // set functions for button in header
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.openSettings) {
            startActivity(Intent(this, Settings::class.java));

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        // load header bar
        this.setSupportActionBar(this.findViewById(R.id.my_toolbar))
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        this.supportActionBar?.setDisplayShowHomeEnabled(true);

        // load notes from storage
        val notes = NotesService.read(this.applicationContext);
        if (notes != null) {
            val textNotes = this.findViewById<EditText>(R.id.notes);

            textNotes.setText(notes)
        } else {
            Toast.makeText(this.applicationContext, "Can't get notes \uD83D\uDE15", Toast.LENGTH_SHORT).show()
        }

        val btnSave = findViewById<FloatingActionButton>(R.id.btnSaveNote);
        btnSave.setOnClickListener {
            val textNotes = findViewById<EditText>(R.id.notes);
            val notes = textNotes.text.toString();

            val notesSaved = NotesService.save(this, notes);

            if (notesSaved) {
                NotesService.broadcast(this);

                Toast.makeText(this, "Notes saved 💪", Toast.LENGTH_SHORT).show()
                this.finish()
            } else {
                Toast.makeText(this, "Can't save notes \uD83D\uDE15", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
