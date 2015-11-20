/*
package com.example.andrejssileckis.fragmenttestactivity;

*/
/**
 * Created by andrejs.sileckis on 10/27/2015.
 *//*


import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SwipeDetector implements OnTouchListener {
    public static enum Action{
        LR,
        RL,
        TB,
        None,
        Click
    }
    private static final String logTag = "SwipeDetector";
    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;

    public boolean swipeDetected(){
        return mSwipeDetected != Action.None;
    }

    public Action getAction(){
        return mSwipeDetected;
    }

    public boolean onTouch(View view, MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = motionEvent.getX();
                downY = motionEvent.getY();
                mSwipeDetected = Action.None;
                return false;
            }
            case MotionEvent.ACTION_UP: {
                upX = motionEvent.getX();
                upY = motionEvent.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                //if horizontal detected
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    //left or right
                    if (deltaX > 0) {
                        mSwipeDetected = Action.RL;
                    }
                    if (deltaX < 0) {
                        mSwipeDetected = Action.LR;
                    }
                }
                mSwipeDetected = Action.Click;
                return false;
            }
        }
        return false;
    }
}
*/
