package com.ken.music.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ken.music.objects.BitRate;
import com.ken.music.objects.LinkDownload;
import com.ken.music.objects.SongOnline;
import com.ken.music.objects.SourcePlay;

public class JsonHandler {
	
	List<SongOnline> lst	  = new ArrayList<SongOnline>(); // list bài hát
	SongOnline song			  = null; // instance of item song online
	BitRate bitRate			  = null; // instance of chất lượng nhạc
	LinkDownload linkDownload = null; // instance of link tải nhạc
	SourcePlay sourcePlay 	  = null; // instance of link chơi nhạc
	JSONArray arrSong; 				  // mảng đối tượng json 
	String bitRateStr;				  // chuỗi hiển thị chất lượng nhạc
	


    ////////////////////////////////////////////////////////////////////////////////
	
	private static String readUrl(String urlString) throws Exception {
		
		BufferedReader reader = null;
		try {
			int read;
			URL url			 	= new URL(urlString);
			reader 				= new BufferedReader(
										new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			char[] chars 		= new char[1024];
			
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}// end-try
	}// end-func readUrl


    ////////////////////////////////////////////////////////////////////////////////

	public List<SongOnline> getListSongByTextSearch(String strSearch) {
		

		// string json from url
		String jsonStr = null;
		try {
			// đường dẫn tìm kiếm
			 String xxx = Vars.BASE_SEARCH_MP3
					 		+ URLEncoder.encode(strSearch, "UTF-8") // chuỗi tìm kiếm
					 		+ Vars.END_BASE_SEARCH;

			// lấy về được chuỗi json
			jsonStr = readUrl(xxx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonStr != null) {
			try {
				JSONObject rootObject = new JSONObject(jsonStr);// root object
				// get node docs
				arrSong = rootObject.getJSONArray(Vars.TAG_DOCS);
				
				// loop thought all songs
				for (int i = 0; i < arrSong.length(); i++) {
					JSONObject songObject = arrSong.getJSONObject(i);
					// khởi tạo các đối tượng để sử dụng lưu lại thông tin
					song 		 = new SongOnline();
					bitRate 	 = new BitRate();
					linkDownload = new LinkDownload();
					sourcePlay	 = new SourcePlay();

					bitRateStr	= songObject.getString(Vars.TAG_BITRATE);
					bitRate		= MyUtils.getBitrateByStr(bitRateStr);

					//set dữ liệu cho đối tượng song
					song.setSong_Id(songObject.getLong(Vars.TAG_SONG_ID))
						.setTitle(URLDecoder.decode(songObject
													.getString(Vars.TAG_TITLE),
													"UTF-8"))
						.setArtist(songObject.getString(Vars.TAG_ARTIST))
						.setGenre(songObject.getString(Vars.TAG_GENRE))
						.setBitrate(bitRate)
						.setDuration(songObject.getInt(Vars.TAG_DURATION))
						.setLink(songObject.getString(Vars.TAG_LINK))
						.setTotal_play(songObject.getInt(Vars.TAG_TOTALPLAY));
					
					// link download node
					JSONObject linkDownloadObject 
								= songObject.getJSONObject(Vars.TAG_LINK_DOWNLOAD);
					// source node
					JSONObject sourceObject
								= songObject.getJSONObject(Vars.TAG_SOURCE);

					// nếu có chất lượng 128kbps
					if (bitRate.isB128()) {
						linkDownload.setS128(linkDownloadObject.getString(Vars.TAG_128));
						sourcePlay	.setS128(sourceObject.getString(Vars.TAG_128));
					}
					// nếu có chất lượng 320kbps
					if (bitRate.isB320()) {
						linkDownload.setS320(linkDownloadObject.getString(Vars.TAG_320));
						sourcePlay	.setS320(sourceObject.getString(Vars.TAG_320));
					}
					// nếu có chất lượng lossless
					if (bitRate.isbLossless()) {
						linkDownload.setsLossLess(linkDownloadObject
												  .getString(Vars.TAG_LOSSLESS));
						sourcePlay.setLossless(sourceObject
											   .getString(Vars.TAG_LOSSLESS));
					}
					
					song.setLinkDownload(linkDownload);
					song.setSourcePLay(sourcePlay);
					song.setLyricsFile(songObject.getString(Vars.TAG_LYRICS_FILE));

					// thêm bài hát vào list 
					lst.add(song);
				}// end-for
			} catch (Exception ex) {
				ex.printStackTrace();
			}// end-try
		} else {
			lst = null;
		}// end-if

		Log.d(">>>>> ken <<<<< ", " JsonParse getListSongByTextSearch ");
		return lst;

	}// end-func getListCommentByPage


    public static List<SongOnline> getDataSong(Context context, String textSearch) {
        List<SongOnline> lst = new ArrayList<SongOnline>();

        // lấy ra danh sách bài hát online với từ khóa tìm kiếm
        lst = (new JsonHandler()).getListSongByTextSearch(textSearch);

        return lst;
    }// end-func getDataSong
}
