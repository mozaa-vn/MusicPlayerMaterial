package com.ken.music.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ken.music.activity.R;
import com.ken.music.objects.SongOnline;
import com.ken.music.utils.MyUtils;

public class SongOnlineAdapter extends BaseAdapter {
	LayoutInflater inflater;
	List<SongOnline> lstData;

	
    ////////////////////////////////////////////////////////////////////////////////
    // TODO getters & setters
	
	public List<SongOnline> getLstData() {
		
		return lstData;
	}

	public void setLstData(List<SongOnline> lstData) {
		
		this.lstData = lstData;
		notifyDataSetChanged();
	}

	
    ////////////////////////////////////////////////////////////////////////////////
    // TODO constructor

	public SongOnlineAdapter(Context context, List<SongOnline> list) {
		
		this.lstData  = list;
		this.inflater = (LayoutInflater) 
						  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Log.d(">>>>> ken <<<<<","SongOnlineAdapter --- constructor");
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
			return lstData.get(position).getSong_Id();
		}
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		SongOnline song = (SongOnline) getItem(position);
		
		if (convertView == null) {
			holder 		= new ViewHolder();
			convertView = inflater.inflate(R.layout.item_song_search_online, parent, false);

			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvArtist = (TextView) convertView.findViewById(R.id.tvArtist);
			holder.tvBitRate = (TextView) convertView.findViewById(R.id.tvBitRate);
			holder.tvTotalPlay = (TextView) convertView.findViewById(R.id.tvTotalPlay);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}// end-if


		// thiết lập chuỗi hiển thị chất lượng nhạc
		String strBitRate = "";
		
		if (song.getBitrate().isB128()) {
			strBitRate += "128 ";
		}// end-if
		
		if (song.getBitrate().isB320()) {
			strBitRate += "| 320";
		}// end-if

		if (song.getBitrate().isbLossless()) {
			strBitRate += " | lossless";
		}// end-if

		// thiết lập các giá trị hiển thị 1 dòng dữ liệu trong listview
		holder.tvTitle.setText(song.getTitle());
		holder.tvArtist.setText(song.getArtist());
		holder.tvBitRate.setText(strBitRate);
		holder.tvTotalPlay
				.setText(MyUtils	
						.replaceTotalPlayToSemantic(
						 String.valueOf(song.getTotal_play())));

		return convertView;
	}

	
////////////////////////////////////////////////////////////////////////////////
// inner class
	
	private class ViewHolder {
		
		private TextView 
				tvTitle,	  // tên bài hát
				tvArtist,	  // tên ca sỹ
				tvBitRate,	  // chất lượng nhạc
				tvTotalPlay;  // tổng số lần được chơi
	}// end-class ViewHolder
	
}// end-class SongAdapter
