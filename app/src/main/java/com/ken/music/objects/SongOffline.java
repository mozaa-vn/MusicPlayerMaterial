package com.ken.music.objects;


public class SongOffline extends Song{

	private String path;
    private long 	id 		= 0;
    private long 	artistID 	= 0;
    private String 	album 		= "";
    private long 	albumID 	= 0;
    private long 	duration 	= 0;
    private String 	dataStream 	= null;
    private String  displayname = "";

	public SongOffline() {}

    ////////////////////////////////////////////////////////////////////////////////
    // TODO getters & setters


    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

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


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public long getArtistID() {
        return artistID;
    }


    public void setArtistID(long artistID) {
        this.artistID = artistID;
    }


    public String getAlbum() {
        return album;
    }


    public void setAlbum(String album) {
        this.album = album;
    }


    public long getAlbumID() {
        return albumID;
    }


    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }


    public long getDuration() {
        return duration;
    }


    public void setDuration(long duration) {
        this.duration = duration;
    }


    public String getDataStream() {
        return dataStream;
    }


    public void setDataStream(String dataStream) {
        this.dataStream = dataStream;
    }


}
