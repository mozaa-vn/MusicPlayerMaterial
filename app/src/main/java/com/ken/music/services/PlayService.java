package com.ken.music.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ken.music.objects.Song;
import com.ken.music.objects.SongOffline;
import com.ken.music.objects.SongOnline;
import com.ken.music.utils.MyUtils;

import java.util.List;

public class PlayService extends Service implements 
												OnCompletionListener, 
												OnErrorListener,
												OnInfoListener, 
												OnBufferingUpdateListener, 
												OnSeekCompleteListener, 
												OnPreparedListener{
	
////////////////////////////////////////////////////////////////////////////////
	
	private MediaPlayer mediaPlayer = new MediaPlayer();
	public static int	NOTIFYCATION_ID = 55;
	
	private NotificationCompat.Builder 	mBuilder;
	private NotificationManager 		mNotificationManager;
	private RemoteViews 				notifiExpandView,
										notifiSmallView;
	private Notification 				notification;
	
	//Action of intent
	public final static String 
			//Action send audio preparing to activity 
			BROADCAST_FEEDBACK = "com.music.activity.broadcastfeedback",
			//Action send update position to activity
			BROADCAST_SEEK = "com.music.activity.seekprogress",
			//Action Activity send seek to service
			BROADCAST_CONTROL = "com.ken.music.controls.control",
			//Action to excute phone ringing
			BROADCAST_TELEPHONE = "com.music.activity.telephone",
			//Action to 
			BROADCAST_BUFFER = "com.music.activity.buffered";
	
	//Key to get/set Extra from/to intent
	public final static String
			NO_ACTION_TOGGLE_PLAY 	= "toggleplayback",
			NO_ACTION_NEXT 			= "nextmedia",
			NO_ACTION_PREVIOUS 		= "previousmedia",
			NO_ACTION_STOP 			= "stopmedia",

			KEY_FEED_ACTION 		= "feedaction",
			KEY_FEEDBACK 			= "feedbacktoactivity",
			KEY_FEEDBACK_ISPLAYING 	= "feedbackisplaying",
					
			KEY_CONTROL_ACTION 		= "controlaction",
			KEY_CONTROL_VALUE 		= "controlvalue",
			KEY_DATA_SONG			= "dataobject",

			KEY_UPDATE_SEEK_POSITION 	= "mediaposition",
			KEY_UPDATE_SEEK_DURATION 	= "mediaduration",
					
			KEY_SELECT_SUB_LIST 		= "controlsublist",
			KEY_SELECT_LIST_POSITION 	= "controllistposition";
				
	public final static int
			ACTION_CONTROL_PLAY			= 0x0,
			ACTION_CONTROL_PAUSE		= 0x1,
			ACTION_CONTROL_PREVIOUS 	= 0x2,
			ACTION_CONTROL_NEXT 		= 0x3,
			ACTION_CONTROL_STOP			= 0x4,
			ACTION_CONTROL_SEEK 		= 0x5,
			ACTION_REQUEST_POSITION		= 0x6,	
			ACTION_CONTROL_REWIND 		= 0x8,					
			ACTION_CONTROL_FAST_FORWARD	= 0x9,
			ACTION_CONTROL_PLAY_NEW		= 0x7,
			
			TYPE_SONG_ONLINE			= 0x0,
			TYPE_SONG_OFFLINE			= 0x1,
					
			LIST_ALL_LOCAL				= 0x1,
			LIST_ALL_ONLINE				= 0x2,
			LIST_ALBUM					= 0x3,
			LIST_ARTIST					= 0x4,
			
			FEED_ACTION_BUFFER 			= 0x1,
			FEED_ACTION_SEND_POSITION 	= 0x2,
			
			VALUE_SEEK			= 5000;
	
	private Intent 	
			feedBackIntent, 	//Intent send preparing
			seekIntent,		//Intent send update position to activity
			bufferedIntent;
	
	// Variable to send positon and duration of media to the activity 
	// (send update position)
	private int
			intMediaPosition,
			intMediaDuration;
	public static int
			intCurrentSong,
			intTotalSong,
			currentSong;

	private String pathPlay = "";
	private SongOffline songOffline = null;
	private SongOnline songOnline	= null;

    private int currentSongPosition = -1;

    public static List<SongOnline> currentListOnline = null;    // current list online
    public static List<SongOffline> currentListOffline = null;  // current list online
	
	// setting telephone manager
	private boolean isPauseInCall = false;
	private PhoneStateListener phoneStateListener;
	private TelephonyManager telephonyManager;
	
	//handler to create the thread to send update position
	private final Handler handler = new Handler();
	
	
////////////////////////////////////////////////////////////////////////////////
// service life cycle
	
	@Override
	public void onCreate() {
		feedBackIntent	= new Intent(BROADCAST_FEEDBACK);	
		seekIntent		= new Intent(BROADCAST_SEEK);
		bufferedIntent	= new Intent(BROADCAST_BUFFER);
		
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnInfoListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.reset();
		
	}// end-func onCreate
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		registerReceiver(controlReceiver, new IntentFilter(BROADCAST_CONTROL));

		return START_STICKY;

	}// end-func onStartCommand
		
		// get the telephone manager
//		telephonyManager   = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		phoneStateListener = new PhoneStateListener(){
//			@Override
//			public void onCallStateChanged(int state, String incomingNumber) {
//				switch(state){
//				case TelephonyManager.CALL_STATE_OFFHOOK:
//					Log.d(">>>>> ken <<<<<", "PlayService --- CALL STATE OFF HOOK");
//					break;
//				
//				case TelephonyManager.CALL_STATE_RINGING:
//					Log.d(">>>>> ken <<<<<", "PlayService --- CALL STATE RINGING");
////					if(mediaPlayer != null){
//						if(mediaPlayer.isPlaying()){
//							pauseMedia();
//							isPauseInCall = true;
//						}
////					}
//					break;
//					
//				case TelephonyManager.CALL_STATE_IDLE:
//					Log.d(">>>>> ken <<<<<", "PlayService --- CALL STATE IDLE");
////					if(mediaPlayer != null){
//						 if(isPauseInCall){
//							 playMedia();
//							 isPauseInCall = false;
//						 }
////					}
//					break;
//				}// end-switch
//				
//				// register the listener with the telephone manager
//				telephonyManager.listen(phoneStateListener,
//										PhoneStateListener.LISTEN_CALL_STATE );
//				
//				super.onCallStateChanged(state, incomingNumber);
//			}// end-func onCallStateChanged
//		};// end-phone state listener
		
		
		// return super.onStartCommand(intent, flags, startId);
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}// end-func onBind
	
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}// end-func onUnbind
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		//stop media
		if (mediaPlayer != null) {
			if(mediaPlayer.isPlaying())
				mediaPlayer.stop();
			
			mediaPlayer.release();
		}// end-if
		
		//unregister telephone manager
		if (phoneStateListener != null) {
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
		}
		
		
		
		unregisterReceiver(controlReceiver);
		
		// 
		
	} // onDestroy

	
	////////////////////////////////////////////////////////////////////////////////
	// implements methods
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			Toast.makeText(this, 
						   "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra, 
						   Toast.LENGTH_LONG).show();
			break;
		
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Toast.makeText(this,
						   "MEDIA ERROR SERVER DIED " + extra,
						   Toast.LENGTH_LONG).show();
			break;
			
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Toast.makeText(this,
						   "MEDIA ERROR UNKNOWN " + extra,
						   Toast.LENGTH_LONG).show();
			break;
			
		default:
			break;
		}// end-switch
		return false;
	}// end-func onError


	@Override
	public void onCompletion(MediaPlayer mp) {
		nextMedia();
	}// end-func onCompletion


	@Override
	public void onPrepared(MediaPlayer mp) {
		
		// send message to activity to end progress dialog
		sendBufferCompletedBroadcast();
		
		playMedia();
	}// end-func onPrepared


	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		
	}// end-func onBufferingUpdate


	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		Log.d(">>>>> ken <<<<<", "PlayService --- onInfo");
		return false;
	}// end-func onInfo

	
////////////////////////////////////////////////////////////////////////////////
// controls media player
	
	private void playMedia(){
		// TODO play
		mediaPlayer.start();
	}
	
	
	private void playNewMedia(int typeOfSong, Song object){
		// TODO play new
		Log.d(">>>>> ken <<<<<", "PlayService --- playNewMedia");
		try{
			// release media 
			mediaPlayer.reset();
			
			if(typeOfSong == TYPE_SONG_OFFLINE){		// nếu bài hát offline
				// khởi tạo đối tượng thực thi
				songOffline = (SongOffline) object;
				
				// lấy ra đường dẫn play
				pathPlay = songOffline.getPath();
								
			} else if (typeOfSong == TYPE_SONG_ONLINE){ // nếu bài hát online
				//khởi tạo đối tượng thực thi
				songOnline = (SongOnline) object;

                // position of song
                currentSongPosition = MyUtils.positionOfSongOnline(currentListOnline,songOnline);

				// kiểm tra chất lượng nhạc để lấy đường dẫn play
				if(songOnline.getBitrate().isB320())
					pathPlay = songOnline.getSourcePLay().getS320();
				else if (songOnline.getBitrate().isB128())
					pathPlay = songOnline.getSourcePLay().getS320();
				
			}// end-if

			// thiết lập data source cho media
			mediaPlayer.setDataSource(pathPlay);
			
			sendBufferingBroadcast();
			mediaPlayer.prepareAsync();
			
			// start media
			mediaPlayer.start();
		}catch(Exception ex){
			Log.d(">>>>> ken <<<<<", Log.getStackTraceString(ex));
		}// end-try
		
	}// end-func playNewMedia
	
	
	private void pauseMedia(){
		// TODO	pause
		mediaPlayer.pause();
	}
	
	
	private void stopMedia(){
		// TODO stop
//		mediaPlayer.stop();
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
	}
	
	
	private void nextMedia(){
		// TODO next
	}
	
	
	private void previousMedia(){
		// TODO previous
	}
	
	
	private void seekMedia(){
		// TODO seek
	}
	
	
	private void fastForwardMedia(){
		// TODO fast forward
	}
	
	
	private void rewindMedia(){
		// TODO rewind
	}
	

////////////////////////////////////////////////////////////////////////////////
// broadcast 
	
	private BroadcastReceiver controlReceiver = new BroadcastReceiver() {	
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(">>>>> ken <<<<<", "1. BroadcastReceiver controlReceiver - SERVICE");
			
			int action = intent.getExtras().getInt(KEY_CONTROL_ACTION);
			
			switch (action) {
			
			case ACTION_CONTROL_PLAY_NEW:
				// lấy dữ liệu từ broadcast
				int typeOfSong = intent.getExtras().getInt(KEY_CONTROL_VALUE);
				Song object = (Song) intent.getSerializableExtra(KEY_DATA_SONG);
				
				// phát bài nhạc mới
				playNewMedia(typeOfSong, object);
				
				// TODO gửi broadcast để cập nhật giao diện
				
				break;
				
			
			case ACTION_CONTROL_PLAY:
				playMedia();
				break;


            case ACTION_CONTROL_PAUSE:
                pauseMedia();
                break;
				
			
			case ACTION_CONTROL_SEEK:
				handler.removeCallbacks(sendUpdate2UI);			
				int seekMediaTo = intent.getIntExtra(KEY_CONTROL_VALUE, 0);							
				mediaPlayer.seekTo(seekMediaTo);			
				handler.postDelayed(sendUpdate2UI, 1000);
				break;
				
				
			case ACTION_CONTROL_NEXT:
				nextMedia();
				break;
				
				
			case ACTION_CONTROL_PREVIOUS:
				previousMedia();
				break;
				
				
			case ACTION_CONTROL_FAST_FORWARD:
				fastForwardMedia();
				break;
				
				
			case ACTION_CONTROL_REWIND:
				rewindMedia();
				break;
				
				
			case ACTION_CONTROL_STOP:
				stopMedia();
				break;
				
				
			case ACTION_REQUEST_POSITION:
//				sendFeedBackBroadcast(FEED_ACTION_SEND_POSITION, intCurrentSong, mediaPlayer.isPlaying());
				break;
			
				
			default:
				break;
			}// end-switch

			Log.d(">>>>> ken <<<<<", "2. BroadcastReceiver controlReceiver - SERVICE, Action = " + action);
		}// end-func onReceive
	}; // end-broadcast controlReceiver
	
	
	//send a message to Activity that audio is being prepared and buffering stated 
	private void sendBufferingBroadcast(){
		bufferedIntent.putExtra("buffering", "1");
		sendBroadcast(bufferedIntent);
	}
	
	//Send a message to Activity that audio is prepared and ready to start playing
	private void sendBufferCompletedBroadcast(){
		bufferedIntent.putExtra("buffering", "0");
		sendBroadcast(bufferedIntent);
	}

///////////////////////////////////////////////////////////////////////////////
	
	
///////////////////////////////////////////////////////////////////////////////
//Thread use to send position update
	private Runnable sendUpdate2UI = new Runnable() {		
		@Override
		public void run() {
			if(mediaPlayer.isPlaying()){
				intMediaPosition = mediaPlayer.getCurrentPosition();
				intMediaDuration = mediaPlayer.getDuration();
				
				seekIntent.putExtra(KEY_UPDATE_SEEK_POSITION, intMediaPosition);
				seekIntent.putExtra(KEY_UPDATE_SEEK_DURATION, intMediaDuration);
				//seekIntent.putExtra(KEY_UPDATE_SEEK_ENDED, songEnd);
				sendBroadcast(seekIntent);
			}//end-if
			
			handler.postDelayed(sendUpdate2UI, 1000);
		}
	};


}
