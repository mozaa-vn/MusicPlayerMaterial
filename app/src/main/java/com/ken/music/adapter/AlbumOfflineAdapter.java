package com.ken.music.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ken.music.activity.R;
import com.ken.music.objects.Album;
import com.ken.music.objects.SongOffline;

import java.util.List;

public class AlbumOfflineAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<Album> lstData;


    ////////////////////////////////////////////////////////////////////////////////
    // TODO getters & setters

	public List<Album> getLstData() {

		return lstData;
	}

	public void setLstData(List<Album> lstData) {

		this.lstData = lstData;
		notifyDataSetChanged();
	}


    ////////////////////////////////////////////////////////////////////////////////
    // TODO constructor

	public AlbumOfflineAdapter(Context context, List<Album> list) {
		
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
		Album item = (Album) getItem(position);
		
		if (convertView == null) {
			holder 		= new ViewHolder();
			convertView = inflater.inflate(R.layout.item_album_offline, parent, false);

			holder.tvTitle  = (TextView) convertView.findViewById( R.id.tvTitleAlbumOffline );
			holder.tvArtist = (TextView) convertView.findViewById( R.id.tvArtistAlbumOffline );

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}// end-if

		// thiết lập các giá trị hiển thị 1 dòng dữ liệu trong listview
		holder.tvTitle.setText(item.getTitle());
		holder.tvArtist.setText(item.getArtist());

		return convertView;
	}

	
    ////////////////////////////////////////////////////////////////////////////////
    // TODO inner class
	
	private class ViewHolder {
		
		private TextView 
                    tvTitle,	  // tên album
                    tvArtist;	  // tên ca sỹ
	}// end-class ViewHolder
	
}// end-class SongAdapter
