package com.ken.music.utils;

import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;

import com.ken.music.objects.BitRate;
import com.ken.music.objects.Song;
import com.ken.music.objects.SongOffline;
import com.ken.music.objects.SongOnline;

public class MyUtils {

	
    ////////////////////////////////////////////////////////////////////////////////
	// TODO get bitrate by string

	public static BitRate getBitrateByStr(String string) {
		
		BitRate bitRate 	= new BitRate();
		StringTokenizer stk = new StringTokenizer(string, "|");

		if (stk != null) {
			while (stk.hasMoreTokens()) {
				String strStk = stk.nextToken().toString().replace(" ", "");

				if (strStk.contains("128")) {
					// Log.d("128 here", "128 here");
					bitRate.setB128(true);
				}
				if (strStk.contains("320")) {
					// Log.d("320 here", "320 here");
					bitRate.setB320(true);
				}
				if (strStk.contains("loss")) {
					// Log.d("lossless here", "lossless here");
					bitRate.setbLossless(true);
				}
			}// end-while
		}// end-if

		return bitRate;
	}// end-func getBitrateByStr


    ////////////////////////////////////////////////////////////////////////////////
    // TODO hide keyboard

	public static void hideKeyboard(Activity activity) {
		
		// Check if no view has focus:
		View view = activity.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager 
								= (InputMethodManager) 
								   activity
								   .getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(),
												 InputMethodManager.HIDE_NOT_ALWAYS);
		}// end-if
	}// end-func hideKeyboard


    ////////////////////////////////////////////////////////////////////////////////
    // TODO replace total play

	public static String replaceTotalPlayToSemantic(String totalPlay) {
		String result  = ""; // kết quả số người play được tách theo mỗi 3 chữ số
		String[] split = totalPlay.split(""); // cắt mỗi ký tự chuỗi vào mảng string

		int j = 0;
		//chạy ngược mảng split
		for (int i = split.length - 1; i > 0; i--) { 
			result += split[i];
			j++;
			if (j < split.length - 1) {
				//mỗi số thứ 3 thì thêm dấu: .
				result += (j % 3 == 0) ? "." : "";
			}// end-if
		}// end-for

		String[] finalOk = result.split("");

		// đảo ngược mảng
		for (int i = 0; i < finalOk.length / 2; i++) {
			String t = finalOk[i];
			finalOk[i] = finalOk[finalOk.length - 1 - i];
			finalOk[finalOk.length - 1 - i] = t;
		}// end-for

		result = "";
		for (int i = 0; i < finalOk.length; i++) { 
			// gán mảng vừa đảo ngược vào kết quả
			result += finalOk[i];
		}// end-for

		return result;
	}// end-func replaceTotalPlayToSemantic


    ////////////////////////////////////////////////////////////////////////////////
    // tODO format second as time

	public static String formatSecondsAsTime(long millis) {
		
		String seconds = String.valueOf((millis / 1000) % 60);
		String minutes = String.valueOf(((millis - Integer.parseInt(seconds)) / 1000) / 60);

		// nếu phút nhỏ hơn 10 thì thêm 0
		if (Integer.parseInt(minutes) < 10) {
			minutes = "0" + minutes;
		}
		// nếu giây nhỏ hơn 10 thì thêm 0
		if (Integer.parseInt(seconds) < 10) {
			seconds = "0" + seconds;
		}
		
		return minutes + ':' + seconds;
	}// end-func formatSecondsAsTime


    ////////////////////////////////////////////////////////////////////////////////
    // TODO check network

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager)
                                 context .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo	   = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO get position of song in list

    public static int positionOfSongOnline(List<SongOnline> listData, SongOnline item ){

        for(SongOnline object : listData){
            if(item.getTitle().equals(object.getTitle()) ){
                return listData.indexOf(object);
            }
        }// end-for

        return -1;
    }

    public static int positionOfSongOffline(List<SongOffline> listData, SongOffline item ){

        for(SongOffline object : listData){
            if(item.getTitle().equals(object.getTitle()) ){
                return listData.indexOf(object);
            }
        }// end-for

        return -1;
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO check is mp3

    public static boolean isMp3(String dataPath){

        StringTokenizer stk = new StringTokenizer(dataPath, ".");

        stk.nextToken();
        String extra = stk.nextToken().toString();
        if(extra.equals("mp3") || extra.equals("flac") ){
            Log.d(">>> ken <<<", "extra :: " + extra );

            return true;
        }

        return false;
    }

}
