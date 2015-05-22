package com.ken.music.objects;

import com.ken.music.controls.LocalLibrary;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 5/22/2015.
 */
public class Playlist {
    public long id = 0;
    public String name = "";
    public String data = "";
    public Date date = null;

    public Playlist() {
    }

//    public ArrayList<SongOffline> getTrackList() {
//        return LocalLibrary.getTrackOfPlayList(id);
//    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name;
    }
}
