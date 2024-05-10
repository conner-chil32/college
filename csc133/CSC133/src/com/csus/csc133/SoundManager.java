package com.csus.csc133;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class SoundManager {
	private Media m;
	private static SoundManager sm = null;
	public static synchronized SoundManager getInstance() {
		if(sm == null) {
			sm = new SoundManager();
		}
		return sm;
	}
	
	public void setup() {
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/bg.mp3");
			m = MediaManager.createMedia(is, "audio/wav");
			m.setVolume(8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play() {
		m.play();
	}
	
	public void pause() {
		m.pause();
	}
}
