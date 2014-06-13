package com.example.gametester;

import com.example.gametester.thread.GameThread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final boolean DEBUG = true;
	
	private int mCurrentFrameRate = 0;
	private Object mThreadLock = new Object();
	
	private TextView mFrameDisplay;
	
	private GameThread mGameLoop;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFrameDisplay = (TextView)findViewById(R.id.debug_frame_display);
        View kill = findViewById(R.id.kill);
        kill.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mGameLoop.closeThread();
			}
		});
        
        mGameLoop = new GameThread(this);
        mGameLoop.start();
    }
    
    @Override
    protected void onDestroy() {
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
    				if(DEBUG) {
    					mFrameDisplay.setText(String.valueOf(mCurrentFrameRate));
    				}

    				synchronized (mThreadLock) {
    					mThreadLock.notify();
    				}
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

}
