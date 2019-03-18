package com.example.simplenotes

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

const val FILE_NAME = "Notes.txt"

class NotesService {
    companion object {
        fun save(context: Context, notes: String): Boolean {
            try {
                val writer = BufferedWriter(OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)))
                val lines = notes.split("\n");
                lines.forEach {
                    if(lines.indexOf(it) > 0)
                        writer.newLine();

                    writer.write(it)
                }
                writer.close()

                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }

        fun read(context: Context): String? {
            try {
                val reader = BufferedReader(InputStreamReader(context.openFileInput(FILE_NAME)))
                val stringBuilder = StringBuilder()
                while (true) {
                    val line = reader.readLine();
                    if (line == null) break;

                    stringBuilder.append(line + "\n")
                }

                reader.close();
                return stringBuilder.toString();
            } catch (e: Exception) {
                e.printStackTrace()
                return null;
            }

        }

        fun broadcast(context: Context) {
            val intent = Intent(context, SimpleNotesWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            val ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, SimpleNotesWidget::class.java!!))

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            context.sendBroadcast(intent)

        }
    }
}