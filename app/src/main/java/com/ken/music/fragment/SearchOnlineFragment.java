package com.ken.music.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ken.music.activity.R;
import com.ken.music.adapter.SongOnlineAdapter;
import com.ken.music.controls.Control;
import com.ken.music.objects.SongOnline;
import com.ken.music.controls.PlayService;
import com.ken.music.utils.MyUtils;
import com.ken.music.utils.Vars;

import java.util.List;

/**
 * Created by neokree on 20/01/15.
 */
public class SearchOnlineFragment extends Fragment {

    private static ListView lvSongSearchOnline;
    public static SongOnlineAdapter adapter;
    private static List<SongOnline> listData;

//    ((MaterialNavigationDrawer)this.getActivity()).setFragmentChild(new ChildFragment(),"Child Title");


    ////////////////////////////////////////////////////////////////////////////////
    // TODO activity life cycle

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_search_online, container, false );

        // init controls
        lvSongSearchOnline = (ListView) view.findViewById(R.id.lvSongSearchOnline);

        // set adapter
        adapter = new SongOnlineAdapter(getActivity(),listData);
        lvSongSearchOnline.setAdapter(adapter);

        // events listview
        lvSongSearchOnline.setOnItemClickListener(lvOnItemClick);
        lvSongSearchOnline.setOnItemLongClickListener(lvOnItemLongClick);

        return view;
    }// end-func onCreateView


    ////////////////////////////////////////////////////////////////////////////////
    // TODO events

    AdapterView.OnItemClickListener lvOnItemClick = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            try {
                // set list data in service class
                PlayService.currentListOnline = listData;

                // state play music from online
                Vars.SONG_IS_WHERE = Vars.SONG_ONLINE;

                // data to send
                SongOnline songSend		= (SongOnline) adapter.getItem(position);

                // send data thought broadcast
                Control.sendPlayNew(PlayService.TYPE_SONG_ONLINE, songSend);

                // show my view control

//                Toast.makeText(getActivity(),PlayService.currentListOnline.size()+"",Toast.LENGTH_SHORT).show();

                // set up data for service
                PlayService.currentSongPosition = MyUtils.positionOfSongOnline(PlayService.currentListOnline, songSend);
                PlayService.currentTotalSong = listData.size();
                PlayService.titleSong = songSend.getTitle();

            } catch (Exception ex) {
                ex.printStackTrace();
            }// end-try

        }
    };// end-event lvOnItemClick

    AdapterView.OnItemLongClickListener lvOnItemLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


            return false;
        }
    };// end-eventlvOnItemLongClick


    ////////////////////////////////////////////////////////////////////////////////
    // TODO function inner class

    public static void updateDataSong(Context context, List<SongOnline> lst) {
        // cập nhật danh sách bài hát trong adapter
        adapter = new SongOnlineAdapter(context, lst);
        lvSongSearchOnline.setAdapter(adapter);
        listData = lst;
    }// end-func updateDataSong


}
