package com.example.simplenotes

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

class SimpleNotesWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val views = RemoteViews(context.packageName, R.layout.simple_notes_widget) // get elementById

            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

            val notes = NotesService.read(context);
            if (notes != null) {
                views.setTextViewText(R.id.appwidget_text, notes)
            } else {
                Toast.makeText(context, "Can't get notes \uD83D\uDE15", Toast.LENGTH_SHORT).show()
            }


            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

