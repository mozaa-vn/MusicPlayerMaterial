package com.ken.music.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.ken.music.controls.Control;
import com.ken.music.controls.PlayService;
import com.ken.music.utils.MyUtils;
import com.ken.music.utils.Vars;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by admin on 5/20/2015.
 */

public class WidgetPlayer extends AppWidgetProvider implements Observer{

    private static final String
                            ACTION_CONTROL_NEXT     = "actionNext",
                            ACTION_CONTROL_PREVIOUS = "actionPrevious",
                            ACTION_CONTROL_PAUSE    = "actionPause",
                            ACTION_CONTROL_PLAY     = "actionPlay",
                            ACTION_CONTROL_STOP     = "actionStop";


    RemoteViews views;
    Context mContext;
    Vars myObserv;

    ////////////////////////////////////////////////////////////////////////////////
    // TODO widget life cycle

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        mContext = context;
        myObserv = (Vars) context.getApplicationContext();
        myObserv.getObserver().addObserver(this);
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
//        mContext = context;
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
        mContext = context;

        if(ACTION_CONTROL_NEXT.equals(intent.getAction())){
            Control.sendControl(PlayService.ACTION_CONTROL_NEXT, null);
            setIsPlay();
        } else if(ACTION_CONTROL_PREVIOUS.equals(intent.getAction())) {
            Control.sendControl(PlayService.ACTION_CONTROL_PREVIOUS, null);
            setIsPlay();
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

    @Override
    public void update(Observable observable, Object data) {
//        views = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);

        // get text
        String textTime =  MyUtils.formatSecondsAsTime(Long.valueOf(myObserv.getObserver().getValue()))+"";

        // set text
        views.setTextViewText(R.id.tvTimeWidget, textTime);

        views.setTextViewText(R.id.tvSongTitleWidget, PlayService.titleSong);

        // update widget
        AppWidgetManager.getInstance(mContext).updateAppWidget( new ComponentName(mContext, WidgetPlayer.class),views);

    }
}
