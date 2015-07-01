package com.ken.music.utils;

import android.app.Application;

import com.ken.music.myinterface.IUpdateOfSong;
import com.ken.music.objects.Album;
import com.ken.music.objects.Artist;
import com.ken.music.objects.Playlist;
import com.ken.music.objects.SongOffline;
import com.ken.music.objects.SongOnline;

import java.util.List;

//import com.music.myinterface.ObsSetCurrentTime;

public class Vars extends Application {

    /*
     * setting up Observer
     */
    IUpdateOfSong mObsCurrentTime;

    @Override
    public void onCreate() {
        super.onCreate();
        mObsCurrentTime = new IUpdateOfSong();
    }

    public IUpdateOfSong getObserver() {
        return mObsCurrentTime;
    }

    /* define some data string
     */
	
	public static final String
                            KEY_CODE = "fafd463e2131914934b73310aa34a23f",
                            BASE_THUMB = "http://image.mp3.zdn.vn/",
                            BASE_SEARCH_MP3 = "http://api.mp3.zing.vn/api/mobile/search/song?requestdata={\"q\":\"",
                            BASE_SEARCH_VIDEO = "http://api.mp3.zing.vn/api/mobile/search/video?requestdata={\"q\":\"",
                            END_BASE_SEARCH = "\",\"sort\":\"hot\"}";

	public static final String
                            SONG = "song",
                            TAG_DOCS = "docs",
                            TAG_SONG_ID = "song_id",
                            TAG_TITLE = "title",
                            TAG_ARTIST = "artist",
                            TAG_GENRE = "genre",
                            TAG_BITRATE = "bitrate",
                            TAG_DURATION = "duration",
                            TAG_TOTALPLAY = "total_play",
                            TAG_LINK = "link",
                            TAG_SOURCE = "source",
                            TAG_LINK_DOWNLOAD = "link_download",
                            TAG_128 = "128",
                            TAG_320 = "320",
                            TAG_LOSSLESS = "lossless",
                            TAG_LYRICS_FILE = "lyrics_file";

	public static final int
                            STATE_SONG_PLAY = 0x0,
							STATE_SONG_PAUSE = 0x1,
							STATE_SONG_STOP = 0x2,
							STATE_SONG_STOP_AGAIN = 0x3;
	public static int STATE_SONG = STATE_SONG_STOP;

	public static final int SONG_ONLINE = 0x0,
							SONG_OFFLINE = 0x1;
	public static int SONG_IS_WHERE = SONG_OFFLINE;


    // data to show
    public static List<SongOffline> listSongOffline     = null;
    public static List<Album>       listAlbumOffline    = null;
    public static List<Artist>      listArtistOffline   = null;
    public static List<Playlist>    listPlaylistOffline = null;

    public static List<SongOffline> listSongOfflineBySomething = null;

    public static List<SongOnline>  listSongOnline  = null;

    public static SongOffline songOffline = null;


	// video info of a video
	// http://api.mp3.zing.vn/api/mobile/video/getvideoinfo?keycode=fafd463e2131914934b73310aa34a23f&requestdata={"id":1074729245}

	// mp3 info of a song
	// http://api.mp3.zing.vn/api/mobile/song/getsonginfo?

	// thumb
	// http://image.mp3.zdn.vn/thumb_video/d/c/dcacff355635deedf62fd80de34f2346_1380622208.jpg

	// mp3 search list
	// http://api.mp3.zing.vn/api/mobile/search/song?requestdata={"q":"text_search_here","sort":"hot"}

	// video search list
	// http://api.mp3.zing.vn/api/mobile/search/video?requestdata={"q":"text_search_here","sort":"hot"}

	// mp3 lyrics of an id
	// http://api.mp3.zing.vn/api/mobile/song/getlyrics?keycode=fafd463e2131914934b73310aa34a23f&requestdata={"id":"1073747936"}

}
