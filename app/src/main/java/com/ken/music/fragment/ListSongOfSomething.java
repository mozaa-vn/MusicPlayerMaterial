package com.ken.music.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ken.music.activity.R;
import com.ken.music.adapter.SongOfflineAdapter;
import com.ken.music.controls.Control;
import com.ken.music.controls.PlayService;
import com.ken.music.objects.SongOffline;
import com.ken.music.utils.MyUtils;
import com.ken.music.utils.Vars;

/**
 * Created by admin on 5/22/2015.
 */
public class ListSongOfSomething extends Fragment {


    private ListView lvSongOffline;
    private SongOfflineAdapter adapter;

    ////////////////////////////////////////////////////////////////////////////////
    // TODO fragment life cycle

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_song_offline, container, false );

        // turn on option menu
        setHasOptionsMenu(true);

        // init controls
        lvSongOffline = (ListView) view.findViewById(R.id.lvSongOffline);

        // events
        lvSongOffline.setOnItemClickListener(lvSongOfflineItemClick);
        lvSongOffline.setOnItemLongClickListener(lvSongOfflineLongClick);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        // set adapter for list view
        adapter = new SongOfflineAdapter(getActivity(), Vars.listSongOfflineBySomething);
        lvSongOffline.setAdapter(adapter);
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO option menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(true);

        super.onCreateOptionsMenu(menu, inflater);
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO events

    AdapterView.OnItemClickListener lvSongOfflineItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {

                // state play music from online
                Vars.SONG_IS_WHERE  = Vars.SONG_OFFLINE;

                // data to send
                SongOffline songSend = (SongOffline) adapter.getItem(position);

                // send data thought broadcast
                Control.sendPlayNew(PlayService.TYPE_SONG_OFFLINE, songSend);

                // set list data in service class
                PlayService.currentListOffline = Vars.listSongOfflineBySomething;

                // set up data for service
                PlayService.intCurrentSong = MyUtils.positionOfSongOffline(PlayService.currentListOffline, songSend);
                PlayService.intTotalSong = Vars.listSongOfflineBySomething.size();
                PlayService.titleSong = songSend.getTitle();

                // show my view control

            } catch (Exception ex) {
                ex.printStackTrace();
            }// end-try
        }
    };

    AdapterView.OnItemLongClickListener lvSongOfflineLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            return false;
        }
    };


    ////////////////////////////////////////////////////////////////////////////////

}
