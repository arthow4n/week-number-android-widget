package com.example.weekwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.RemoteViews;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekWidgetProvider extends AppWidgetProvider {

    private static final String PREFS_NAME = "WidgetConfig";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

        String widgetText = prefs.getString("widget_text", "Vecka");
        int textColor = prefs.getInt("text_color", Color.WHITE);
        int backgroundColor = prefs.getInt("background_color", Color.BLACK);
        int backgroundTransparency = prefs.getInt("background_transparency", 50);

        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(new Locale("sv", "SE"));
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        views.setTextViewText(R.id.widget_label, widgetText);
        views.setTextViewText(R.id.widget_week_number, String.valueOf(weekNumber));
        views.setTextColor(R.id.widget_label, textColor);
        views.setTextColor(R.id.widget_week_number, textColor);

        int alpha = (int) (255 * (backgroundTransparency / 100.0));
        views.setInt(R.id.widget_root, "setBackgroundColor", Color.argb(alpha, Color.red(backgroundColor), Color.green(backgroundColor), Color.blue(backgroundColor)));

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}