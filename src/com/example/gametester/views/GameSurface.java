package com.example.gametester.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = GameSurface.class.getSimpleName();
	
	private int x = 0;
	private int y = 0;
	
	private int pri = Color.parseColor("#00C0C0");
	private int sec = Color.parseColor("#2D2DFF");
	private int rectSize = 50;
	
	private SurfaceHolder mHolder;
	private Paint mPainter = new Paint();
	
	public GameSurface(Context context) {
		super(context);
		init(context);
	}
	
	public GameSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GameSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		rectSize = (int)(rectSize * density);
		mHolder = getHolder();
		mHolder.addCallback(this);
	}
	
	//TODO: move GameThread into GameSurface, out of MainActivity
	public void draw(Canvas c) {
		int useX;		//width of the first rect
		int useY;
		useX = rectSize - x;
		useY = rectSize - y;
		int switchX = 0;
		int switchY = 0;
		if(c == null) return;
		
		//i and j are the actual x and y coordinates of the surface view,
		// and the left/top corner of each square to draw.
		// j = x, i = y
		//TODO FIXME does not render these correctly. Try with x,y values nonzero.
		for(int i = 0; i < c.getHeight(); i += rectSize) {
			switchX = switchY;
			for(int j = 0; j < c.getWidth(); j += rectSize) {
				int right;
				int bottom;
				if(j < useX) {
					//first block on this row
					right = useX;
				}
				else {
					right = j + rectSize;
					if(right > c.getWidth()) {
						right = c.getWidth();
					}
				}
				if(i < useY) {
					bottom = useY;
				}
				else {
					bottom = i + rectSize;
					if(bottom > c.getHeight()) {
						bottom = c.getHeight();
					}
				}
				
				if(switchX % 2 == 0) {
					mPainter.setColor(pri);
				}
				else {
					mPainter.setColor(sec);
				}
				c.drawRect(j, i, right, bottom, mPainter);
				
				switchX++;
			}
			switchY += 1;
		}
	}

	
	private boolean isCreated = false;
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		isCreated = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	
	public boolean isCreated() {
		return isCreated;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isCreated = false;
	}
	
	
	
	

}
