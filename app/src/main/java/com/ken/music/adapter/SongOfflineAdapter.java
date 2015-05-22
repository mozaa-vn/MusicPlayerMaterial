package com.ken.music.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ken.music.activity.R;
import com.ken.music.objects.SongOffline;
import com.ken.music.objects.SongOnline;
import com.ken.music.utils.MyUtils;

import java.util.List;

public class SongOfflineAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<SongOffline> lstData;


    ////////////////////////////////////////////////////////////////////////////////
    // TODO getters & setters

	public List<SongOffline> getLstData() {

		return lstData;
	}

	public void setLstData(List<SongOffline> lstData) {

		this.lstData = lstData;
		notifyDataSetChanged();
	}


    ////////////////////////////////////////////////////////////////////////////////
    // TODO constructor

	public SongOfflineAdapter(Context context, List<SongOffline> list) {
		
		this.lstData  = list;
		this.inflater = (LayoutInflater) 
						  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Log.d(">>> ken <<<","SongOfflineAdapter --- constructor");
	}// end-constructor

	
    ////////////////////////////////////////////////////////////////////////////////
    // TODO implements methods
	
	@Override
	public int getCount() {
		
		if (lstData != null) {
			return lstData.size();
		}
		return 0;
	}

	
	@Override
	public Object getItem(int position) {
		
		if (lstData != null && position >= 0 && position < getCount()) {
			return lstData.get(position);
		}
		return null;
	}

	
	@Override
	public long getItemId(int position) {
		
		if (lstData != null && position >= 0 && position < getCount()) {
			return lstData.get(position).getId();
		}
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		SongOffline song = (SongOffline) getItem(position);
		
		if (convertView == null) {
			holder 		= new ViewHolder();
			convertView = inflater.inflate(R.layout.item_song_offline, parent, false);

			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitleSongOffline);
			holder.tvArtist = (TextView) convertView.findViewById(R.id.tvArtistSongOffline);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}// end-if

		// thiết lập các giá trị hiển thị 1 dòng dữ liệu trong listview
		holder.tvTitle.setText(song.getTitle());
		holder.tvArtist.setText(song.getArtist());

		return convertView;
	}

	
    ////////////////////////////////////////////////////////////////////////////////
    // TODO inner class
	
	private class ViewHolder {
		
		private TextView 
                    tvTitle,	  // tên bài hát
                    tvArtist;	  // tên ca sỹ
	}// end-class ViewHolder
	
}// end-class SongAdapter
