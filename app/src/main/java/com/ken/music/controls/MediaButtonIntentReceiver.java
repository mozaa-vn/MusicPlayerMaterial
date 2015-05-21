package com.ken.music.controls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.KeyEvent;

import com.ken.music.utils.Vars;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by admin on 5/21/2015.
 */
public class MediaButtonIntentReceiver extends BroadcastReceiver implements Observer {

    Vars myObserv;

    public MediaButtonIntentReceiver(Context context) {
        super();
        myObserv = (Vars) context.getApplicationContext();
        myObserv.getObserver().addObserver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction = intent.getAction();
        if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            return;
        }
        KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (event == null) {
            return;
        }
        int action = event.getAction();
        if (action == KeyEvent.ACTION_DOWN) {

            // do something
            int keycode = event.getKeyCode();
            if(keycode == KeyEvent.KEYCODE_MEDIA_NEXT) {
                Log.d("TestApp", "Next Pressed");
                Control.sendControl(PlayService.ACTION_CONTROL_NEXT, null);

            } else if(keycode == KeyEvent.KEYCODE_MEDIA_PREVIOUS) {
                Log.d("TestApp", "Previous pressed");
                Control.sendControl(PlayService.ACTION_CONTROL_PREVIOUS, null);

            } else if(keycode == KeyEvent.KEYCODE_HEADSETHOOK) {
                Log.d(">>> ken <<<", "Head Set Hook pressed");
                if(myObserv.getObserver().getIsPlaying()){
                    Control.sendControl(PlayService.ACTION_CONTROL_PAUSE, null);
                    myObserv.getObserver().setIsPlaying(false);
                } else {
                    Control.sendControl(PlayService.ACTION_CONTROL_PLAY, null);
                    myObserv.getObserver().setIsPlaying(true);
                }


            } else if(keycode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
                Log.d("TestApp", "Head Set PAUSE pressed");
                Control.sendControl(PlayService.ACTION_CONTROL_PAUSE, null);

            } else if(keycode == KeyEvent.KEYCODE_MEDIA_PLAY) {
                Log.d("TestApp", "Head Set PLAY pressed");
                Control.sendControl(PlayService.ACTION_CONTROL_PLAY, null);

            } else if(keycode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                Log.d("TestApp", "Head Set PAUSE+PLAY pressed");

            }
        }
        abortBroadcast();
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
