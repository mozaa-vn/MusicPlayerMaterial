package com.ken.music.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.ken.music.controls.Control;
import com.ken.music.controls.LocalLibrary;
import com.ken.music.fragment.ChildFragment;
import com.ken.music.fragment.HomeFragment;
import com.ken.music.fragment.RankSongsFragment;
import com.ken.music.fragment.SearchOnlineFragment;
import com.ken.music.objects.SongOnline;
import com.ken.music.controls.PlayService;
import com.ken.music.utils.JsonHandler;
import com.ken.music.utils.MyUtils;
import com.ken.music.utils.Vars;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class MainActivity extends MaterialNavigationDrawer{



    ////////////////////////////////////////////////////////////////////////////////
    // TODO activity life cycle

    @Override
    public void init(Bundle bundle) {
        this.setDrawerHeaderImage(R.drawable.mat3);

        // create control
        new Control(MainActivity.this);

        // get list Song offline
        Vars.listSongOffline    = LocalLibrary.getAllSong();
        Vars.listAlbumOffline   = LocalLibrary.getAllAlbum();
        Vars.listArtistOffline  = LocalLibrary.getAllArtist();

        // set section
//        this.addSection(newSection("Section 3", R.mipmap.ic_mic_white_24dp, new SearchOnlineFragment()).setSectionColor(getResources().getColor(R.color.color_toolbar_light)));

        this.addSection(newSection("Trang Chủ", R.drawable.icon_home_menu, new HomeFragment()));
        this.addSection(newSection("Bảng Xếp Hạng", R.mipmap.ic_mic_white_24dp, new RankSongsFragment()));

    }// end-func init


    @Override
    protected void onStart() {
        super.onStart();
        this.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        // setup service & control
        Intent intentService = new Intent(MainActivity.this, PlayService.class);
        startService(intentService);

        Log.d(">>>>> ken <<<<<", "MainActivity --- onStart");

    }// end-func onStart


    @Override
    public void onHomeAsUpSelected() {
        // when the back arrow is selected this method is called
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO option menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        if( getCurrentSection().getTargetFragment() instanceof HomeFragment
            || getCurrentSection().getTargetFragment() instanceof RankSongsFragment ){

            MenuItem item=menu.findItem(R.id.search);
            item.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }// end-func onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.search:
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }// end-func onOptionsItemSelected



    ////////////////////////////////////////////////////////////////////////////////
    // TODO function inner class


    ////////////////////////////////////////////////////////////////////////////////
    // TODO events



    ////////////////////////////////////////////////////////////////////////////////
    // TODO asynctask




}
