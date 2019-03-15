package com.example.simplenotes

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

const val FILE_NAME = "Notes.txt"


class NotesStorage {
    companion object {
        fun save(appContext: Context, notes: String): Boolean {
            try {
                val outputStreamWriter = OutputStreamWriter(appContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE))
                outputStreamWriter.write(notes)
                outputStreamWriter.close()

                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }

        fun read(appContext: Context): String? {
            try {
                val stringBuilder = StringBuilder()

                val fileInputStream = appContext.openFileInput(FILE_NAME)
                val reader = BufferedReader(InputStreamReader(fileInputStream))
                while (true) {
                    val line = reader.readLine();
                    if (line == null) break;

                    stringBuilder.append(line)
                }

                fileInputStream.close();
                return stringBuilder.toString();
            } catch (e: Exception) {
                e.printStackTrace()
                return null;
            }

        }
    }
}