package com.example.fittobe.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetListAdapter extends RemoteViewsService {

    @Override
    public WidgetRemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }
}