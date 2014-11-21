package com.jskaleel.fte.common;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnListItemTouchListener implements OnTouchListener {

    @SuppressWarnings("deprecation")
	private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    float downX;
    boolean isMoveEnd;
    boolean isLongPressed;
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
    	
    	if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
		{
    		isLongPressed = false;
    		downX = motionEvent.getX();
		}
		else if(motionEvent.getAction()==MotionEvent.ACTION_MOVE)
		{
			if(isMoveEnd)
				downX = motionEvent.getX();
			isMoveEnd = false;
			onMove(motionEvent.getX()-downX);
		}
		else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
		{   
			isMoveEnd = true;
			if(!isLongPressed)
			onUp(motionEvent.getX()-downX,motionEvent.getX());
		}
		else if(motionEvent.getAction()==MotionEvent.ACTION_CANCEL)
		{   
			isMoveEnd = true;
			if(!isLongPressed)
			onCancel(motionEvent.getX()-downX);
		}
    	
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends SimpleOnGestureListener 
    {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent event) 
        {
            return true;
        }

        
     		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return super.onSingleTapConfirmed(e);
		}

     		

		@Override
			public void onLongPress(MotionEvent e) {
				isLongPressed = true;
				super.onLongPress(e);
				onHover();
			}


		@Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                	
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    
    public void onMove(float difference)  {
    }
    
    public void onUp(float difference,float touchUpX)  {
    }
    
    public void onCancel(float difference)  {
    }
    
    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    
    public void onSwipeBottom() {
    }
    
    public void onHover(){
    }
    
}