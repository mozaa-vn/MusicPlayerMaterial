package com.ken.music.controls;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.ken.music.objects.Album;
import com.ken.music.objects.Artist;
import com.ken.music.objects.SongOffline;
import com.ken.music.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/22/2015.
 */
public class LocalLibrary {

    public static final String
            COLUMN_ID 		    = MediaStore.Audio.Media._ID,
            COLUMN_TITLE 		= MediaStore.Audio.Media.TITLE,
            COLUMN_ARTIST_ID 	= MediaStore.Audio.Media.ARTIST_ID,
            COLUMN_ARTIST 		= MediaStore.Audio.Media.ARTIST,
            COLUMN_ALBUM 		= MediaStore.Audio.Media.ALBUM,
            COLUMN_ALBUM_ID 	= MediaStore.Audio.Media.ALBUM_ID,
            COLUMN_DURATION 	= MediaStore.Audio.Media.DURATION,
            COLUMN_DATA 		= MediaStore.Audio.Media.DATA,
            COLUMN_DISPLAY_NAME = MediaStore.Audio.Media.DISPLAY_NAME,

            COLUMN_ALBUM_ART	= MediaStore.Audio.Albums.ALBUM_ART,
            COLUMN_ALBUM_TRACK	= MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            COLUMN_ALBUM_YEAR	= MediaStore.Audio.Albums.FIRST_YEAR,

            COLUMN_ARTIST_ALBUM = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
            COLUMN_ARTIST_TRACK = MediaStore.Audio.Artists.NUMBER_OF_TRACKS,

            COLUMN_PLAYLIST_NAME = MediaStore.Audio.Playlists.NAME,
            COLUMN_PLAYLIST_DATA = MediaStore.Audio.Playlists.DATA,
            COLUMN_PLAYLIST_DATE = MediaStore.Audio.Playlists.DATE_ADDED;


    public static ContentResolver mContentResolver = Control.context.getContentResolver();


    ////////////////////////////////////////////////////////////////////////////////
    // TODO


    public static List<SongOffline> getAllSong () {

        String selection = MediaStore.Audio.Media.IS_MUSIC + "=1";

        return queryForSongs( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, selection, null, COLUMN_TITLE);

    }// end-func getAllSong


    public static List<Album> getAllAlbum(){
        String selection = null;
        return queryForAlbum( MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
                              selection, null, COLUMN_ALBUM);
    }// end-func getAllAlbum


    public static List<Artist> getAllArtist() {
        String selection = null;
        Cursor cur = mContentResolver.query( MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                                             null, selection, null, COLUMN_ARTIST);

        if (cur == null) {
            return null;
        }

        if (!cur.moveToFirst()) {
            return null;
        }

        List<Artist> artistList = new ArrayList<>();

        int idColumn 		= cur.getColumnIndex(COLUMN_ID);
        int artistColumn 	= cur.getColumnIndex(COLUMN_ARTIST);
        int numAlbumColumn 	= cur.getColumnIndex(COLUMN_ARTIST_ALBUM);
        int numTrackColumn 	= cur.getColumnIndex(COLUMN_ARTIST_TRACK);

        do {
            Artist ar = new Artist();

            ar.setID( cur.getLong(idColumn));
            ar.setArtist( cur.getString(artistColumn));
            ar.setNumTrack( cur.getInt(numTrackColumn));
            ar.setNumAlbum( cur.getInt(numAlbumColumn));

            artistList.add(ar);
        } while (cur.moveToNext());

        return artistList;
    }// end-func getAllArtist


    ////////////////////////////////////////////////////////////////////////////////


    // TODO Query for Songs
    private static List<SongOffline> queryForSongs(Uri uri,
                                                        String[] projection,
                                                        String selection,
                                                        String[] args,
                                                        String sortOrder) {

        Cursor cur = mContentResolver.query(uri, projection, selection, args, sortOrder);

        return cursorToSongList( cur );
    }// end-func queryForSongs


    // TODO get list Song by cursor
    public static List<SongOffline> cursorToSongList(Cursor cur){
        if (cur == null) {
            return null;
        }

        if (!cur.moveToFirst()) {
            return null;
        }

        List<SongOffline> list = new ArrayList<SongOffline>();

        int idColumn 		= cur.getColumnIndex(COLUMN_ID);
        int titleColumn 	= cur.getColumnIndex(COLUMN_TITLE);
        int artistColumn 	= cur.getColumnIndex(COLUMN_ARTIST);
        int artistIDColumn 	= cur.getColumnIndex(COLUMN_ARTIST_ID);
        int albumColumn 	= cur.getColumnIndex(COLUMN_ALBUM);
        int albumIDColumn 	= cur.getColumnIndex(COLUMN_ALBUM_ID);
        int durationColumn 	= cur.getColumnIndex(COLUMN_DURATION);
        int dataColumn 		= cur.getColumnIndex(COLUMN_DATA);
        int displayName     = cur.getColumnIndex(COLUMN_DISPLAY_NAME);

        do {
            SongOffline si = new SongOffline();

            si.setTitle( cur.getString(titleColumn));
            si.setArtist( cur.getString(artistColumn));
            si.setAlbum(cur.getString(albumColumn));
            si.setDuration( cur.getLong(durationColumn));
            si.setPath( cur.getString(dataColumn));
            si.setId(cur.getLong(idColumn));
            si.setArtistID(cur.getLong(artistIDColumn));
            si.setAlbumID( cur.getLong(albumIDColumn));
            si.setDisplayname( cur.getString(displayName));

            if(MyUtils.isMp3(si.getDisplayname())){
                list.add(si);
            }
        } while (cur.moveToNext());

        cur.close();

        return list;
    }// end-func cursorToSongList


    // TODO Query for Album
    private static ArrayList<Album> queryForAlbum(Uri uri, String[] projection, String selection, String[] args,
                                                      String sortOrder) {

        Cursor cur = mContentResolver.query( uri, projection, selection, args, sortOrder);

        if (cur == null) {
            return null;
        }

        if (!cur.moveToFirst()) {
            return null;
        }

        ArrayList<Album> albumlist = new ArrayList<>();

        int idColumn 		= cur.getColumnIndex(COLUMN_ID);
        int titleColumn 	= cur.getColumnIndex(COLUMN_ALBUM);
        int artistColumn 	= cur.getColumnIndex(COLUMN_ARTIST);
        int artColumn 		= cur.getColumnIndex(COLUMN_ALBUM_ART);
        int trackColumn 	= cur.getColumnIndex(COLUMN_ALBUM_TRACK);
        int yearColumn 		= cur.getColumnIndex(COLUMN_ALBUM_YEAR);

        do {
            Album al = new Album();

            al.setId( cur.getLong(idColumn));
            al.setTitle( cur.getString(titleColumn));
            al.setArtist( cur.getString(artistColumn));
            al.setAlbumArt( cur.getString(artColumn));
            al.setTrack( cur.getInt(trackColumn));
            al.setYear( cur.getInt(yearColumn));

            albumlist.add(al);
        } while (cur.moveToNext());

        return albumlist;
    }// end-func queryForAlbum


    public static List<SongOffline> getTrackOfAlbum(long AlbumID){
        String selection = COLUMN_ALBUM_ID + " = " + AlbumID;
        return queryForSongs( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, selection, null, COLUMN_TITLE);
    }// end-func getTrackOfAlbum


    public static List<SongOffline> getTrackOfArtist(long ArtistID){
        String selection = COLUMN_ARTIST_ID + " = " + ArtistID;

        return queryForSongs( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,  selection, null, COLUMN_TITLE);
    }// end-func getTrackOfArtist


    public static List<Album> getAlbumOfArtist(long ArtistID){

        String selection = null;
        return queryForAlbum( MediaStore.Audio.Artists.Albums.getContentUri("external", ArtistID),
                null,  selection, null, null);
    }// end-func getAlbumOfArtist



}
