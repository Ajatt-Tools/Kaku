package ca.fuwafuwa.kaku;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by 0x1bad1d3a on 4/13/2016.
 */
public class CaptureWindow extends Window implements CaptureWindowCallback  {

    private String TAG = this.getClass().getName();

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    public CaptureWindow(MainService context) {
        super(context);

        ((WindowView) mWindow.findViewById(R.id.capture_window)).registerCallback(this);
        ((ResizeView) mWindow.findViewById(R.id.resize_box)).registerCallback(this);
    }

    private void setOpacity(MotionEvent e){
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mWindow.setAlpha(0.4f);
                break;
            case MotionEvent.ACTION_UP:
                mWindow.setAlpha(0.1f);
                break;
        }
    }

    @Override
    public boolean onMoveEvent(MotionEvent e) {
        setOpacity(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                initialTouchX = e.getRawX();
                initialTouchY = e.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                mContext.saveImage(params.x, params.y, params.width, params.height);
                return true;
            case MotionEvent.ACTION_MOVE:
                params.x = initialX + (int) (e.getRawX() - initialTouchX);
                params.y = initialY + (int) (e.getRawY() - initialTouchY);
                mWindowManager.updateViewLayout(mWindow, params);
                return true;
        }
        Log.e(TAG, "NOTHING");
        return false;
    }

    @Override
    public boolean onResizeEvent(MotionEvent e) {
        setOpacity(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.width;
                initialY = params.height;
                initialTouchX = e.getRawX();
                initialTouchY = e.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_MOVE:
                params.width = initialX + (int) (e.getRawX() - initialTouchX);
                params.height = initialY + (int) (e.getRawY() - initialTouchY);
                mWindowManager.updateViewLayout(mWindow, params);
                return true;
        }
        return true;
    }
}