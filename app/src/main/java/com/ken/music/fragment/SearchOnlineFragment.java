package com.ken.music.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ken.music.activity.R;
import com.ken.music.adapter.SongOnlineAdapter;
import com.ken.music.controls.Control;
import com.ken.music.objects.SongOnline;
import com.ken.music.controls.PlayService;
import com.ken.music.utils.JsonHandler;
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

        // turn on for options menu
        setHasOptionsMenu(true);

        return view;
    }// end-func onCreateView


    ////////////////////////////////////////////////////////////////////////////////
    // TODO options menu

    String mSearchString;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(true);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView       = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getActivity().getComponentName()) );

        final Toast toast = new Toast(getActivity());
        if (searchView != null ) {
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
            {
                public boolean onQueryTextChange(String newText)
                {
                    mSearchString = newText;
                    //doFilterAsync(mSearchString);

                    return true;
                }

                public boolean onQueryTextSubmit(String query)
                {
                    mSearchString = query;
                    //doFilterAsync(mSearchString);
                    if(mSearchString != ""){
                        new LoadSongSearchOnline().execute(mSearchString);
                    }

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }// end-if

        super.onCreateOptionsMenu(menu, inflater);
    }// end-func onCreateOptionsMenu


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
                PlayService.intCurrentSong = MyUtils.positionOfSongOnline(PlayService.currentListOnline, songSend);
                PlayService.intTotalSong = listData.size();
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




    private class LoadSongSearchOnline extends AsyncTask<String, Void, List<SongOnline>> {
        private final ProgressDialog dialog = new ProgressDialog(
                getActivity());

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loadding..");
            dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected List<SongOnline> doInBackground(String... params) {
            return JsonHandler.getDataSong(getActivity(), params[0]);
        }

        @Override
        protected void onPostExecute(List<SongOnline> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (result != null && result.size() > 0) {
                SearchOnlineFragment.updateDataSong(getActivity(), result);
            } else {
                Toast.makeText(getActivity(),
                        "Không có kết quả phù hợp!", Toast.LENGTH_SHORT).show();
            }
        }
    }// end-async LoadSong

}
