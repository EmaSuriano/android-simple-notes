package com.example.simplenotes

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */
class SimpleNotesWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            Log.d("TAG", "onUpdate")
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            Log.d("TAG", "updateAppWidget")


            val views = RemoteViews(context.packageName, R.layout.simple_notes_widget) // get elementById

            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

//            val widgetText = context.getString(R.string.appwidget_text) // text from the layout
            val notes = NotesStorage.read(context);
            if (notes != null) {
                views.setTextViewText(R.id.appwidget_text, notes) //set text
//                textNotes.setText(notes)
            } else {
                Toast.makeText(context, "Can't get notes \uD83D\uDE15", Toast.LENGTH_SHORT).show()
            }


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

