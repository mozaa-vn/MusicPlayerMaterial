package com.ken.music.objects;

import com.ken.music.controls.LocalLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/22/2015.
 */
public class Album {

    private long id = 0;
    private String title = "";
    private String artist = "";
    private String albumArt = "";
    private int track = 0;
    private int year = 0;

    public Album(){
    }

    public List<SongOffline> getTrackList() {
        return LocalLibrary.getTrackOfAlbum(id);
    }



    // todo getters & setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    // todo implements methods

    @Override
    public String toString() {
        return albumArt + " - " + title;
    }




}
