package com.example.fittobe.Widget;

import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.content.Context;
import com.example.fittobe.Models.Exercise;
import com.example.fittobe.R;

import java.util.List;

class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
   private Context context;
    private List<Exercise> exercises;
    public WidgetRemoteViewsFactory(Context context){
        this.context = context;
    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        exercises = ExerciseAppWidgetProvider.getExercises();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_card);
        Exercise e = exercises.get(i);
        views.setTextViewText(R.id.exercise_info, e.getName());
        Log.d("MyLog",e.getName());
        return views;    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
