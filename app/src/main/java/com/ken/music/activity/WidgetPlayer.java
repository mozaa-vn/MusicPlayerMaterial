package com.ken.music.activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.ken.music.controls.Control;
import com.ken.music.services.PlayService;

/**
 * Created by admin on 5/20/2015.
 */

public class WidgetPlayer extends AppWidgetProvider {

    private static final String
                            ACTION_CONTROL_NEXT     = "actionNext",
                            ACTION_CONTROL_PREVIOUS = "actionPrevious",
                            ACTION_CONTROL_PAUSE    = "actionPause",
                            ACTION_CONTROL_PLAY     = "actionPlay",
                            ACTION_CONTROL_STOP     = "actionStop";


    RemoteViews views;

    ////////////////////////////////////////////////////////////////////////////////
    // TODO widget life cycle

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }


    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
            int appWidgetId = appWidgetIds[i];

            // tạo 1 cái remote view để diều khiển khi ở màn hình chủ
            views= new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // thiet lap su kien
            views.setOnClickPendingIntent(R.id.ivNextWidget, getPendingSelfIntent(context, ACTION_CONTROL_NEXT));
            views.setOnClickPendingIntent(R.id.ivPreviousWidget, getPendingSelfIntent(context, ACTION_CONTROL_PREVIOUS));
            views.setOnClickPendingIntent(R.id.ivPauseWidget, getPendingSelfIntent(context, ACTION_CONTROL_PAUSE));
            views.setOnClickPendingIntent(R.id.ivPlayWidget, getPendingSelfIntent(context, ACTION_CONTROL_PLAY));
            views.setOnClickPendingIntent(R.id.ivStopWidget, getPendingSelfIntent(context, ACTION_CONTROL_STOP));

            // update widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if(ACTION_CONTROL_NEXT.equals(intent.getAction())){
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

//            RemoteViews remoteViews;
//            ComponentName watchWidget;

//            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//            watchWidget = new ComponentName(context, Widget.class);

//            remoteViews.setTextViewText(R.id.sync_button, "TESTING");
//            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        } else if(ACTION_CONTROL_STOP.equals(intent.getAction())){
            Control.sendControl(PlayService.ACTION_CONTROL_STOP, null);
            setIsNotPlay();
        } else if(ACTION_CONTROL_PAUSE.equals(intent.getAction())){
            Control.sendControl(PlayService.ACTION_CONTROL_PAUSE, null);
            setIsNotPlay();
        } else if(ACTION_CONTROL_PLAY.equals(intent.getAction())){
            Control.sendControl(PlayService.ACTION_CONTROL_PLAY, null);
            setIsPlay();
        }

        AppWidgetManager.getInstance(context).updateAppWidget( new ComponentName(context, WidgetPlayer.class),views);
    }


    ////////////////////////////////////////////////////////////////////////////////

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    private void setIsPlay(){
        views.setViewVisibility(R.id.ivPlayWidget, View.GONE);
        views.setViewVisibility(R.id.ivPauseWidget, View.VISIBLE);
    }

    private void setIsNotPlay(){
        views.setViewVisibility(R.id.ivPlayWidget, View.VISIBLE);
        views.setViewVisibility(R.id.ivPauseWidget, View.GONE);
    }
}
