package com.ken.music.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.ken.music.controls.Control;
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

        // set section
//        this.addSection(newSection("Section 3", R.mipmap.ic_mic_white_24dp, new SearchOnlineFragment()).setSectionColor(getResources().getColor(R.color.color_toolbar_light)));

        this.addSection(newSection("Bảng Xếp Hạng", R.mipmap.ic_mic_white_24dp, new RankSongsFragment()));
        this.addSection(newSection("Tìm Kiếm Online", R.mipmap.ic_mic_white_24dp, new SearchOnlineFragment()));

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        // setup service & control
        Intent intentService = new Intent(MainActivity.this, PlayService.class);
        startService(intentService);
        new Control(MainActivity.this);

        Log.d(">>>>> ken <<<<<", "MainActivity --- onStart");
    }


    @Override
    public void onHomeAsUpSelected() {
        // when the back arrow is selected this method is called
    }


    ////////////////////////////////////////////////////////////////////////////////
    // TODO option menu

    String mSearchString;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView       = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );

        final Toast toast = new Toast(this);

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
                        // check current fragment
                        if( getCurrentSection().getTargetFragment() instanceof SearchOnlineFragment ){
                            // set data for listview in search online fragment
                            if(MyUtils.isOnline(MainActivity.this)){
                                new LoadSongSearchOnline().execute(mSearchString);
                            }

                        } else if (getCurrentSection().getTargetFragment() instanceof RankSongsFragment){
                            toast.makeText(getApplicationContext(), "đây là fragment RANK SONGS", Toast.LENGTH_LONG).show();
                        }
                    }

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }// end-if

        return true;
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
    // TODO events



    ////////////////////////////////////////////////////////////////////////////////
    // TODO asynctask


    private class LoadSongSearchOnline extends AsyncTask<String, Void, List<SongOnline>> {
        private final ProgressDialog dialog = new ProgressDialog(
                MainActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loadding..");
            dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected List<SongOnline> doInBackground(String... params) {
            return JsonHandler.getDataSong(MainActivity.this, params[0]);
        }

        @Override
        protected void onPostExecute(List<SongOnline> result) {
            MyUtils.hideKeyboard(MainActivity.this);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (result != null && result.size() > 0) {
                SearchOnlineFragment.updateDataSong(MainActivity.this, result);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Không có kết quả phù hợp!", Toast.LENGTH_SHORT).show();
            }
        }
    }// end-async LoadSong

}
