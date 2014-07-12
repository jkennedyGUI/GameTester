package com.example.gametester.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.gametester.MainActivity;
import com.example.gametester.views.GameSurface;

public class GameThread extends Thread {
	@SuppressWarnings("unused")
	private static final String TAG = "GameThread";
	
	private long lastTime = 0;
	private volatile boolean mRunFlag = false;
	
	private MainActivity mActivity;
	private GameSurface mSurface;
	private SurfaceHolder mSurfaceHolder;
	
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
			if(elapsed != 0) {
				rate = (int)(1e9 / elapsed);
			}
			this.lastTime = time;
			this.mActivity.setFrameRate(rate);
			this.mActivity.updateDisplay();
			
			Canvas c = mSurfaceHolder.lockCanvas();
			synchronized(mSurfaceHolder) {
				mSurface.draw(c);
			}
			if(c != null) {
				mSurfaceHolder.unlockCanvasAndPost(c);
			}
			//state
			
			//input
			
//			updateState();
//		    updateInput();
//		    updateAI();
//		    updatePhysics();
//		    updateAnimations();
//		    updateSound();
			
			
		}
		//save game state here - game cycle terminated.
	}
	
	public void setActivity(MainActivity activity) {
		this.mActivity = activity;
	}
	
	public void setSurfaceView(GameSurface surface) {
		mSurface = surface;
		mSurfaceHolder = mSurface.getHolder();
	}
	
	public void closeThread() {
		this.mRunFlag = false;
	}
}
