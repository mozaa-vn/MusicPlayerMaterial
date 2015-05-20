package com.ken.music.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ken.music.activity.R;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by ken on 20/01/15.
 */

public class RankSongsFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        Log.i("MaterialNavigationDrawer Master-Child", "Master created");

        View view = inflater.inflate(R.layout.fragment_rank_songs,container,false);
        ((Button)view.findViewById(R.id.master_button)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ((MaterialNavigationDrawer)this.getActivity()).setFragmentChild(new ChildFragment(),"Child Title");
    }
}
