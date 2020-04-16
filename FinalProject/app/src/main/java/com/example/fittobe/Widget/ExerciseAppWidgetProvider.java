package com.example.fittobe.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.fittobe.Models.Exercise;
import com.example.fittobe.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAppWidgetProvider extends AppWidgetProvider {
    private static List<Exercise> mExercises = new ArrayList<>();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds, List<Exercise> exercises) {
        mExercises=exercises;
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.exercises_widget);
            Intent intent = new Intent(context, WidgetListAdapter.class);
            views.setRemoteAdapter(R.id.widget_favorite_list, intent);
            ComponentName component = new ComponentName(context, ExerciseAppWidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_favorite_list);
            appWidgetManager.updateAppWidget(component, views);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }



    public static List<Exercise> getExercises(){
        return mExercises;
    }
}
