package com.example.andrejssileckis.medialearnactivity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;

import com.example.andrejssileckis.fragmenttestactivity.R;

import java.lang.reflect.Field;

/**
 * Custom MediaController that fixes issue with controls appearing offset on pre 4.3 devices
 * and shows how to add additional functionality such as fullscreen button
 */
public class CustomMediaController extends MediaController {
    private Context mContext;
    private OnMediaControlerInteractionListener mListener;

    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CustomMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
        mContext = context;
    }

    public CustomMediaController(Context context) {
        super(context);
        mContext = context;
    }

    public interface OnMediaControlerInteractionListener {
        void onRequestFullScreen();
    }
    public void setListener(OnMediaControlerInteractionListener listener){
        mListener = listener;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameParams.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;

        ImageButton fullScreenButton = (ImageButton) LayoutInflater.from(mContext)
                .inflate(R.layout.video_media_controller, null);

        fullScreenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onRequestFullScreen();
                }
            }
        });
        addView(fullScreenButton, frameParams);
    }

    @Override
    public void show(int timeout) {
        super.show(timeout);

        int currentapiVersion = Build.VERSION.SDK_INT;
        if(currentapiVersion < Build.VERSION_CODES.JELLY_BEAN_MR2){
            try{
                Field field1 = MediaController.class.getDeclaredField("mAnchor");
                field1.setAccessible(true);
                View mAnchor = (View) field1.get(this);

                Field field2 = MediaController.class.getDeclaredField("mDecor");
                field2.setAccessible(true);
                View mDecor = (View) field2.get(this);

                Field field3 = MediaController.class.getDeclaredField("mDecorLayoutParams");
                field3.setAccessible(true);
                WindowManager.LayoutParams mDecorLayoutParams =
                        (WindowManager.LayoutParams) field3.get(this);

                Field field4 = MediaController.class.getDeclaredField("mWindowManager");
                field4.setAccessible(true);
                WindowManager mWindowManager = (WindowManager)field4.get(this);

                int[] anchorPos = new int[2];
                mAnchor.getLocationOnScreen(anchorPos);

                mDecor.measure(MeasureSpec.makeMeasureSpec(mAnchor.getWidth(), MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(mAnchor.getHeight(), MeasureSpec.AT_MOST));
                mDecor.setPadding(0, 0, 0, 0);

                WindowManager.LayoutParams params = mDecorLayoutParams;
                params.verticalMargin =  0;
                params.horizontalMargin = 0;
                params.width = mAnchor.getWidth();
                params.height = mAnchor.getHeight();
                params.gravity = Gravity.LEFT|Gravity.TOP;
                params.x = anchorPos[0];
                params.y = anchorPos[1] + mAnchor.getHeight() - mDecor.getMeasuredHeight();
                mWindowManager.updateViewLayout(mDecor, mDecorLayoutParams);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}