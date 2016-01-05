package com.example.andrejssileckis.medialearnactivity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

/**
 * Created by andrejs.sileckis on 12/29/2015.
 */
public class CustomisedMediaController extends MediaController {

    public CustomisedMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomisedMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public CustomisedMediaController(Context context) {
        super(context);
    }
}
