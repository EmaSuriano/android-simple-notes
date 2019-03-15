package com.example.simplenotes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    // populate menu with menu_main.xml
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // close application on back
    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this, "Changes discarded", Toast.LENGTH_LONG).show()
        this.finish()
        return super.onSupportNavigateUp()
    }

    // set functions for button in header
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_note) {
            val textNotes = findViewById<EditText>(R.id.notes);
            val notes = textNotes.text.toString();
            val notesSaved = NotesService.save(this, notes);

            if (notesSaved) {
                NotesService.broadcast(this);

                Toast.makeText(this, "Notes saved 💪", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Can't save notes \uD83D\uDE15", Toast.LENGTH_LONG).show()
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // load header bar
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        // load notes from storage
        val notes = NotesService.read(applicationContext);
        if (notes != null) {
            val textNotes = findViewById<EditText>(R.id.notes);
            textNotes.setText(notes)
        } else {
            Toast.makeText(applicationContext, "Can't get notes \uD83D\uDE15", Toast.LENGTH_SHORT).show()
        }


    }
}
