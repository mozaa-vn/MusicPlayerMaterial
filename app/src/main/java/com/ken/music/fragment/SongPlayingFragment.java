package com.ken.music.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ken.music.activity.R;
import com.ken.music.controls.Control;
import com.ken.music.controls.PlayService;
import com.ken.music.utils.MyUtils;
import com.ken.music.utils.Vars;

import java.util.Observable;
import java.util.Observer;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by admin on 7/1/2015.
 */
public class SongPlayingFragment extends Fragment implements Observer{

    View view;
    Vars myObserv;

    TextView tvCurrentTimePlaying, tvTotalTimePlaying;
    SeekBar sbPlaying;
    ImageView ivPreviousPlaying, ivPausePlaying, ivPlayPlaying, ivNextPlaying, ivStopPlaying;

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.fragment_song_playing,container,false);

        initControls();

        // setup observer
        myObserv = (Vars) getActivity().getApplicationContext();
        myObserv.getObserver().addObserver(this);
        Control.context = getActivity();

        // set total time of song
        tvTotalTimePlaying.setText(MyUtils.formatSecondsAsTime(Vars.songOffline.getDuration()));

        // set seek bar
        sbPlaying.setMax((int) Vars.songOffline.getDuration());
        sbPlaying.setEnabled(false);

        // events
        ivPreviousPlaying.setOnClickListener(ivPreviousPlayingEvent);
        ivPausePlaying.setOnClickListener(ivPausePlayingEvent);
        ivPlayPlaying.setOnClickListener(ivPlayPlayingEvent);
        ivNextPlaying.setOnClickListener(ivNextPlayingEvent);
        ivStopPlaying.setOnClickListener(ivStopPlayingEvent);

        return view;
    }


    /**
     * TODO: init controls
     */
    private void initControls () {

        sbPlaying = (SeekBar) view.findViewById(R.id.sbPlaying);
        tvCurrentTimePlaying = (TextView) view.findViewById(R.id.tvCurrentTimePlaying);
        tvTotalTimePlaying = (TextView) view.findViewById(R.id.tvTotalTimePlaying);

        ivPreviousPlaying = (ImageView) view.findViewById(R.id.ivPreVious);
        ivNextPlaying = (ImageView) view.findViewById(R.id.ivNext);
        ivPlayPlaying = (ImageView) view.findViewById(R.id.ivPlay);
        ivPausePlaying = (ImageView) view.findViewById(R.id.ivPause);
        ivStopPlaying = (ImageView) view.findViewById(R.id.ivStop);
    }


    /**
     * TODO: ivPreViousEvent
     */
    OnClickListener ivPreviousPlayingEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Control.sendControl(PlayService.ACTION_CONTROL_PREVIOUS, null);
            setIsPlay();
            ((MaterialNavigationDrawer) getActivity()).setTitle(PlayService.titleSong);
        }
    };

    /**
     * TODO: ivNextEvent
     */
    OnClickListener ivNextPlayingEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Control.sendControl(PlayService.ACTION_CONTROL_NEXT, null);
            setIsPlay();
            ((MaterialNavigationDrawer) getActivity()).setTitle(PlayService.titleSong);
        }
    };

    /**
     * TODO: ivNextEvent
     */
    OnClickListener ivPlayPlayingEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Control.sendControl(PlayService.ACTION_CONTROL_PLAY, null);
            setIsPlay();
        }
    };

    OnClickListener ivPausePlayingEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Control.sendControl(PlayService.ACTION_CONTROL_PAUSE, null);
            setIsNotPlay();
        }
    };

    OnClickListener ivStopPlayingEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Control.sendControl(PlayService.ACTION_CONTROL_STOP, null);
            setIsNotPlay();
            tvCurrentTimePlaying.setText("00:00");
        }
    };


    /**
     * TODO: set is playing or not playing
     */
    private void setIsPlay(){
        ivPlayPlaying.setVisibility(View.GONE);
        ivPausePlaying.setVisibility(View.VISIBLE);
    }

    private void setIsNotPlay(){
        ivPlayPlaying.setVisibility(View.VISIBLE);
        ivPausePlaying.setVisibility(View.GONE);
    }

    /**
     * TODO: create option menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(false);

        MenuItem itemSetting=menu.findItem(R.id.action_settings);
        itemSetting.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * TODO: update from service with observer method
     */
    @Override
    public void update(Observable observable, Object data) {
        try {
            // get text
            String textTime = MyUtils.formatSecondsAsTime(Long.valueOf(myObserv.getObserver().getValue())) + "";

            // set current time
            tvCurrentTimePlaying.setText(textTime);
            sbPlaying.setProgress( Integer.parseInt(myObserv.getObserver().getValue()) );

            if (myObserv.getObserver().getIsPlaying()) {
                setIsPlay();
            } else {
                setIsNotPlay();
            }
        } catch (Exception ex) {

        }
    }
}
