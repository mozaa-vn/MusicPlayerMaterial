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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.ken.music.activity.R;
import com.ken.music.adapter.AlbumOfflineAdapter;
import com.ken.music.adapter.SongOfflineAdapter;
import com.ken.music.controls.LocalLibrary;
import com.ken.music.objects.Album;
import com.ken.music.utils.Vars;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by admin on 5/22/2015.
 */

public class AlbumOfflineFragment extends Fragment {

    private ListView lvAlbumOffline;
    private AlbumOfflineAdapter adapter;

    ////////////////////////////////////////////////////////////////////////////////
    // TODO fragment life cycle

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_album_offline, container, false );

        // turn on option menu
        setHasOptionsMenu(true);

        // init controls
        lvAlbumOffline = (ListView) view.findViewById(R.id.lvAlbumOffline);

        // events
        lvAlbumOffline.setOnItemClickListener(lvAlbumOfflineItemClick);
        lvAlbumOffline.setOnItemLongClickListener(lvAlbumOfflineLongClick);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        // set adapter for list view
        adapter = new AlbumOfflineAdapter(getActivity(), Vars.listAlbumOffline);
        lvAlbumOffline.setAdapter(adapter);
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(true);

        super.onCreateOptionsMenu(menu, inflater);
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO events

    OnItemClickListener lvAlbumOfflineItemClick = new OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // get item
            Album item = (Album) adapter.getItem(position);

            // get album id
            long albumId = adapter.getItemId(position);

            // set list song offline by album id
            Vars.listSongOfflineBySomething = LocalLibrary.getTrackOfAlbum(albumId);

            // start child fragment to show list songs
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new ListSongOfSomething(), "" +item.getTitle());
        }
    };


    OnItemLongClickListener lvAlbumOfflineLongClick = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            return false;
        }
    };


    ////////////////////////////////////////////////////////////////////////////////

}
