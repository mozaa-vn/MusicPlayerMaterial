package com.ken.music.objects;

import com.ken.music.controls.LocalLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/22/2015.
 */
public class Artist {

    private long ID = 0;
    private String Artist = "";
    private int numAlbum = 0;
    private int numTrack = 0;

    public Artist() {
    }

    // todo function

    public List<Album> getAlbumList() {
        return LocalLibrary.getAlbumOfArtist(ID);
    }

    public List<SongOffline> getTrackOfArtist() {
        return LocalLibrary.getTrackOfArtist(ID);
    }

    ////////////////////////////////////////////////////////////////////////////////
    // TODO getters & setters

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public int getNumAlbum() {
        return numAlbum;
    }

    public void setNumAlbum(int numAlbum) {
        this.numAlbum = numAlbum;
    }

    public int getNumTrack() {
        return numTrack;
    }

    public void setNumTrack(int numTrack) {
        this.numTrack = numTrack;
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO implements methods

    @Override
    public String toString() {
        return Artist;
    }
}
