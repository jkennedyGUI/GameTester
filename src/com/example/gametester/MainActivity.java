package com.example.gametester;

import com.example.gametester.thread.GameThread;
import com.example.gametester.views.GameSurface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final boolean DEBUG = true;
	
	private int mCurrentFrameRate = 0;
	private Object mThreadLock = new Object();
	
	private TextView mFrameDisplay;
	private GameSurface mSurface;
	
	private GameThread mGameLoop;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFrameDisplay = (TextView)findViewById(R.id.debug_frame_display);
        
        mSurface = (GameSurface)findViewById(R.id.game_surface);
        
        mGameLoop = new GameThread(this);
        mGameLoop.setSurfaceView(mSurface);
        mGameLoop.start();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mGameLoop.closeThread();
    }
    
    
    public void setFrameRate(int framerate) {
    	this.mCurrentFrameRate = framerate;
    }

    public void updateDisplay() {
    	synchronized (mThreadLock) {
    		Runnable ui = new Runnable() {
    			@Override
    			public void run() {
    				doUpdateDisplay();
    			}
    		};
    		runOnUiThread(ui);
    		try {
    			mThreadLock.wait();
    		}
    		catch(InterruptedException e) {
    			Log.e(TAG, "InterruptedException", e);
    		}
    	}
    }
    
    private void doUpdateDisplay() {
    	if(DEBUG) {
			mFrameDisplay.setText(String.valueOf(mCurrentFrameRate));
		}

		synchronized (mThreadLock) {
			mThreadLock.notify();
		}
    }

}
