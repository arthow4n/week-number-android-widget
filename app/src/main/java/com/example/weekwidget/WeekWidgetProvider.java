package com.example.weekwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class WeekWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Get the current week number using Swedish locale for ISO 8601 standard
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.forLanguageTag("sv-SE"));
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());

        // Construct the RemoteViews object
        // These R references will be resolved once the layout is created.
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.appwidget_text, String.valueOf(weekNumber));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
