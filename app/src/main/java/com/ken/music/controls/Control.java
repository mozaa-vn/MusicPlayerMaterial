package com.ken.music.controls;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ken.music.objects.Song;
import com.ken.music.services.PlayService;

public class Control {
	
	public static Intent controlIntent = new Intent(PlayService.BROADCAST_CONTROL);
	
	public static Context context;
	
	public static boolean isPlaying = false;


    ////////////////////////////////////////////////////////////////////////////////
    // TODO constructor

    public Control(Context _context){
		context = _context;
	}


    ////////////////////////////////////////////////////////////////////////////////
    // TODO base method send a control

    public static void sendControl( int action, Integer value){
		
		controlIntent.putExtra(PlayService.KEY_CONTROL_ACTION, action);
		
		if( value != null)
			controlIntent.putExtra(PlayService.KEY_CONTROL_VALUE, value.intValue());
		
		context.sendBroadcast(controlIntent);
		Log.d(">>>>> ken <<<<<","Control --- sendControls");
	}// end-func sendControl


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send Next

    public static void sendNext(){
		
		sendControl(PlayService.ACTION_CONTROL_NEXT, null);
	}// end-func sendNext


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send Previous
	
	public static void sendPrevious(){
		
		sendControl(PlayService.ACTION_CONTROL_PREVIOUS, null);
	}// end-func sendPrevious


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send Play

	public static void sendPlay(){
		
		sendControl(PlayService.ACTION_CONTROL_PLAY, null);
	}// end-func sendPlay


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send PlayNew

	public static void sendPlayNew(int typeOfSong, Song object){
		controlIntent.putExtra(PlayService.KEY_DATA_SONG, object );	
		sendControl(PlayService.ACTION_CONTROL_PLAY_NEW, typeOfSong);
	}// end-func sendPlayNew


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send Pause
	
	public static void sendPause(){
		
		sendControl(PlayService.ACTION_CONTROL_PAUSE, null);
	}// end-func sendPause


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send Stop

	public static void sendStop(){
		
		sendControl(PlayService.ACTION_CONTROL_STOP, null);
	}//end-func sendStop


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send FastForward
	
	public static void sendFastForward(){
		
		sendControl(PlayService.ACTION_CONTROL_FAST_FORWARD, null);
	}// end-func sendFastForward

    ////////////////////////////////////////////////////////////////////////////////
    // TODO send Rewind
	
	public static void sendRewind(){
		
		sendControl(PlayService.ACTION_CONTROL_REWIND, null);
	}// end-func sendRewind


    ////////////////////////////////////////////////////////////////////////////////
    // TODO send Seek

	public static void sendSeek(){
		
		sendControl(PlayService.ACTION_CONTROL_SEEK, null);
	}// end-func sendSeek
	
	
}
