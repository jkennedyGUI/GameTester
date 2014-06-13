package com.example.gametester.thread;

import android.util.Log;

import com.example.gametester.MainActivity;

public class GameThread extends Thread {
	private int framerate = 0;
	private long lastTime = 0;
	private volatile boolean mRunFlag = false;
	
	private MainActivity mActivity;
	
	public GameThread() {
		
	}
	
	public GameThread(MainActivity activity) {
		this.mActivity = activity;
	}

	@Override
	public void run() {
		this.mRunFlag = true;
		loop();
	}
	
	
	private void loop() {
		//load game state? pre-loop procedure
		lastTime = System.nanoTime();
		
		while(this.mRunFlag) {
			//game loop
			long time = System.nanoTime();
			long elapsed = time - lastTime;
			int rate = 0;
			Log.d("TAG", "elapsed " + elapsed);
			if(elapsed != 0) {
				rate = (int)(1e9 / elapsed);
			}
			lastTime = time;
			this.mActivity.setFrameRate(rate);
			this.mActivity.updateDisplay();
			
			//state
			
			
			//input
			
			
			
		}
		//save game state here - game cycle terminated.
	}
	
	public void setActivity(MainActivity activity) {
		this.mActivity = activity;
	}
	
	public void closeThread() {
		this.mRunFlag = false;
	}
}
