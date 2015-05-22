package com.ken.music.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ken.music.activity.R;
import com.ken.music.controls.LocalLibrary;
import com.ken.music.utils.Vars;

import org.w3c.dom.Text;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by ken on 20/01/15.
 */

public class HomeFragment extends Fragment {


    private RelativeLayout
                rlOfflineMusic,
                rlOfflineArtist,
                rlOfflinePlayList,
                rlOfflineAlbum,
                rlOnlineSearch,
                rlOnlineBxh,
                rlOnlineBlogTruyen;

    private TextView
                tvTotalSongbanner,
                tvSongTotalView,
                tvAlbumTotalView,
                tvArtistTotalView,
                tvPlaylistTotalView;

    View mView;


    ////////////////////////////////////////////////////////////////////////////////
    // TODO fragment life cycle

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // root view
        mView = inflater.inflate(R.layout.fragment_home,container,false);

        // init controls
        initControls();

        // events
        rlOfflineMusic		.setOnClickListener(rlOfflineMusicEvent);
        rlOfflineArtist		.setOnClickListener(rlOfflineArtistEvent);
        rlOfflinePlayList	.setOnClickListener(rlOfflinePlayListEvent);
        rlOfflineAlbum		.setOnClickListener(rlOfflineAlbumEvent);
        rlOnlineSearch		.setOnClickListener(rlOnlineSearchEvent);
        rlOnlineBxh			.setOnClickListener(rlOnlineBxhEvent);
        rlOnlineBlogTruyen	.setOnClickListener(rlOnlineBlogTruyenEvent);

        // set text size off data offline
        tvTotalSongbanner.setText(Vars.listSongOffline.size()+"");
        tvSongTotalView  .setText(Vars.listSongOffline.size() + "");
        tvAlbumTotalView .setText(Vars.listAlbumOffline.size() +"");
        tvArtistTotalView.setText(Vars.listArtistOffline.size() +"");
//        tvPlaylistTotalView.setText(Vars.listPlaylistOffline.size() +"");

        return mView;
    }// end-func onCreateView



    ////////////////////////////////////////////////////////////////////////////////
    // TODO init controls

    private void initControls(){

        rlOnlineBxh			= (RelativeLayout) mView.findViewById(R.id.rlOnlineBxh);
        rlOfflineAlbum		= (RelativeLayout) mView.findViewById(R.id.rlOfflineAlbum);
        rlOnlineSearch		= (RelativeLayout) mView.findViewById(R.id.rlOnlineSearch);
        rlOfflineMusic		= (RelativeLayout) mView.findViewById(R.id.rlOfflineMusic);
        rlOfflineArtist		= (RelativeLayout) mView.findViewById(R.id.rlOfflineArtist);
        rlOfflinePlayList	= (RelativeLayout) mView.findViewById(R.id.rlOfflinePlayList);
        rlOnlineBlogTruyen	= (RelativeLayout) mView.findViewById(R.id.rlOnlineBlogTruyen);

        tvTotalSongbanner   = (TextView) mView.findViewById(R.id.tvTotalSongbanner);
        tvSongTotalView     = (TextView) mView.findViewById(R.id.tvSongTotalView);
        tvAlbumTotalView    = (TextView) mView.findViewById(R.id.tvAlbumTotalView);
        tvArtistTotalView   = (TextView) mView.findViewById(R.id.tvArtistTotalView);
        tvPlaylistTotalView = (TextView) mView.findViewById(R.id.tvPlaylistTotalView);

    }// end-func initControls


    ////////////////////////////////////////////////////////////////////////////////
    // TODO events

    OnClickListener rlOfflineMusicEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new SongOfflineFragment(),"Bài Hát");
        }
    };// end-event rlOfflineMusicEvent


    OnClickListener rlOfflineArtistEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new ArtistOfflineFragment(),"Ca Sỹ");
        }
    };// end-event rlOfflineArtistEvent


    OnClickListener rlOfflinePlayListEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new ChildFragment(),"PlayList");
        }
    };// end-event rlOfflinePlayListEvent


    OnClickListener rlOfflineAlbumEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new AlbumOfflineFragment(),"Album");
        }
    }; //end-event rlOfflineAlbumEvent


    OnClickListener	rlOnlineSearchEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new SearchOnlineFragment(),"Tìm Kiếm");
        }
    }; //end-event rlOnlineSearchEvent


    OnClickListener rlOnlineBxhEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new ChildFragment(),"Bảng Xếp Hạng");
        }
    }; //end-event rlOnlineBxhEvent


    OnClickListener rlOnlineBlogTruyenEvent= new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MaterialNavigationDrawer) getActivity()).setFragmentChild(new ChildFragment(),"Blog Truyện");
        }
    }; //end-event rlOnlineBlogTruyenEvent


    ////////////////////////////////////////////////////////////////////////////////
    // TODO asynctask




}
