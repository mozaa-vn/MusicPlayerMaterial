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
import com.ken.music.adapter.ArtistOfflineAdapter;
import com.ken.music.adapter.SongOfflineAdapter;
import com.ken.music.controls.LocalLibrary;
import com.ken.music.objects.Album;
import com.ken.music.objects.Artist;
import com.ken.music.utils.Vars;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by admin on 5/22/2015.
 */
public class ArtistOfflineFragment extends Fragment {
    private ListView lvArtistOffline;
    private ArtistOfflineAdapter adapter;

    ////////////////////////////////////////////////////////////////////////////////
    // TODO fragment life cycle

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_artist_offline, container, false );


        // turn on option menu
        setHasOptionsMenu(true);

        // init controls
        lvArtistOffline = (ListView) view.findViewById(R.id.lvArtistOffline);

        // events
        lvArtistOffline.setOnItemClickListener(lvArtistOfflineItemClick);
        lvArtistOffline.setOnItemLongClickListener(lvArtistOfflineLongClick);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        // set adapter for list view
        adapter = new ArtistOfflineAdapter(getActivity(), Vars.listArtistOffline);
        lvArtistOffline.setAdapter(adapter);
    }



    // TODO options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(true);

        super.onCreateOptionsMenu(menu, inflater);
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO events

    AdapterView.OnItemClickListener lvArtistOfflineItemClick = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // get item
            Artist item = (Artist) adapter.getItem(position);

            // get album id
            long artistId = adapter.getItemId(position);

            // set list song offline by album id
            Vars.listSongOfflineBySomething = LocalLibrary.getTrackOfArtist(artistId);

            // start child fragment to show list songs
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new ListSongOfSomething(), "" +item.getArtist());
        }
    };


    AdapterView.OnItemLongClickListener lvArtistOfflineLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            return false;
        }
    };


    ////////////////////////////////////////////////////////////////////////////////

}
