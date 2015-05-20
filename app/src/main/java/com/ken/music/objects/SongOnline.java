package com.ken.music.objects;


public class SongOnline extends Song {
	// private static final Song song = new Song();
	private long song_Id;
	private String genre;
	private int duration;
	private int total_play;
	private String link;
	private BitRate bitrate;
	private LinkDownload linkDownload;
	private SourcePlay sourcePLay;
	private String lyricsFile;

	// public static Song getInstance(){
	// return song;
	// }

	public SongOnline() {}

	public long getSong_Id() {
		return song_Id;
	}

	public SongOnline setSong_Id(long song_Id) {
		this.song_Id = song_Id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public SongOnline setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getArtist() {
		return artist;
	}

	public SongOnline setArtist(String artist) {
		this.artist = artist;
		return this;
	}

	public String getGenre() {
		return genre;
	}

	public SongOnline setGenre(String genre) {
		this.genre = genre;
		return this;
	}

	public int getDuration() {
		return duration;
	}

	public SongOnline setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public int getTotal_play() {
		return total_play;
	}

	public SongOnline setTotal_play(int total_play) {
		this.total_play = total_play;
		return this;
	}

	public BitRate getBitrate() {
		return bitrate;
	}

	public SongOnline setBitrate(BitRate bitrate) {
		this.bitrate = bitrate;
		return this;
	}

	public LinkDownload getLinkDownload() {
		return linkDownload;
	}

	public SongOnline setLinkDownload(LinkDownload linkDownload) {
		this.linkDownload = linkDownload;
		return this;
	}

	public String getLink() {
		return link;
	}

	public SongOnline setLink(String link) {
		this.link = link;
		return this;
	}

	public String getLyricsFile() {
		return lyricsFile;
	}

	public SongOnline setLyricsFile(String lyricsFile) {
		this.lyricsFile = lyricsFile;
		return this;
	}

	public SourcePlay getSourcePLay() {
		return sourcePLay;
	}

	public SongOnline setSourcePLay(SourcePlay sourcePLay) {
		this.sourcePLay = sourcePLay;
		return this;
	}

}
