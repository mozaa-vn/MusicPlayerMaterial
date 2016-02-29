package com.ken.music.controls;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ken.music.objects.Song;

public class Control {
	
	public static Intent controlIntent = new Intent(PlayService.BROADCAST_CONTROL);
	public static Context context;
	public static boolean isPlaying = false;

        ////////////////////////////////////////////////////////////////////////////////
    
        public Control(Context _context){
		context = _context;
	}


	////////////////////////////////////////////////////////////////////////////////

        public static void sendControl( int action, Integer value){
		
		controlIntent.putExtra(PlayService.KEY_CONTROL_ACTION, action);
		
		if( value != null)
			controlIntent.putExtra(PlayService.KEY_CONTROL_VALUE, value.intValue());
		
		context.sendBroadcast(controlIntent);
	}// end-func sendControl


        public static void sendNext(){
		
		sendControl(PlayService.ACTION_CONTROL_NEXT, null);
	}// end-func sendNext

	
	public static void sendPrevious(){
		
		sendControl(PlayService.ACTION_CONTROL_PREVIOUS, null);
	}// end-func sendPrevious


	public static void sendPlay(){
		
		sendControl(PlayService.ACTION_CONTROL_PLAY, null);
	}// end-func sendPlay


	public static void sendPlayNew(int typeOfSong, Song object){
		controlIntent.putExtra(PlayService.KEY_DATA_SONG, object );	
		sendControl(PlayService.ACTION_CONTROL_PLAY_NEW, typeOfSong);
	}// end-func sendPlayNew

	
	public static void sendPause(){
		
		sendControl(PlayService.ACTION_CONTROL_PAUSE, null);
	}// end-func sendPause


	public static void sendStop(){
		
		sendControl(PlayService.ACTION_CONTROL_STOP, null);
	}//end-func sendStop


	public static void sendFastForward(){
		
		sendControl(PlayService.ACTION_CONTROL_FAST_FORWARD, null);
	}// end-func sendFastForward


	public static void sendRewind(){
		
		sendControl(PlayService.ACTION_CONTROL_REWIND, null);
	}// end-func sendRewind


	public static void sendSeek(){
		
		sendControl(PlayService.ACTION_CONTROL_SEEK, null);
	}// end-func sendSeek
	
}
