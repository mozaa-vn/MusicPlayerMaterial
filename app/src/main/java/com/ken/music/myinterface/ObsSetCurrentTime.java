package com.ken.music.myinterface;

import java.util.Observable;

public class ObsSetCurrentTime extends Observable{
	private String currentTime = "00:00";
    private boolean isPlaying = false;

    public boolean getIsPlaying(){
        return isPlaying;
    }

    public void setIsPlaying(boolean state){
        this.isPlaying = state;
        setChanged();
        notifyObservers();
    }

	public String getValue() {
		return currentTime;
	}


	public void setValue(String _currentTime) {
		this.currentTime = _currentTime;
		setChanged();
		notifyObservers();
	}
}
