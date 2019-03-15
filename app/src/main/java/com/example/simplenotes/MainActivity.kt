package com.example.simplenotes

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCancel = findViewById<Button>(R.id.btnCancel);
        btnCancel.setOnClickListener { this.finish() }

        val textNotes = findViewById<EditText>(R.id.notes);
        val btnSave = findViewById<Button>(R.id.btnSave);

        btnSave.setOnClickListener {
            val notes = textNotes.text.toString();
            val notesSaved = NotesStorage.save(applicationContext, notes);

            if (notesSaved) {
                finish()

                val intent = Intent(this, SimpleNotesWidget::class.java)
                intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

                val ids = AppWidgetManager.getInstance(application)
                    .getAppWidgetIds(ComponentName(application, SimpleNotesWidget::class.java!!))

                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                sendBroadcast(intent)

            } else {
                Toast.makeText(applicationContext, "Can't save notes \uD83D\uDE15", Toast.LENGTH_SHORT).show()
            }
        }


        val notes = NotesStorage.read(applicationContext);
        if (notes != null) {
            textNotes.setText(notes)
        } else {
            Toast.makeText(applicationContext, "Can't get notes \uD83D\uDE15", Toast.LENGTH_SHORT).show()
        }


    }
}
