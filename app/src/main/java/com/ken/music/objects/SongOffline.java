package com.ken.music.objects;


public class SongOffline extends Song{
	private String path;

	public SongOffline() {}

	// getters & setters
	public String getTitle() {
		return title;
	}

	public SongOffline setTitle(String _title) {
		this.title = _title;
		return this;
	}

	public String getArtist() {
		return artist;
	}

	public SongOffline setArtist(String _artist) {
		this.artist = _artist;
		return this;
	}
	
	public String getPath() {
		return path;
	}

	public SongOffline setPath(String _path) {
		this.path = _path;
		return this;
	}


}
