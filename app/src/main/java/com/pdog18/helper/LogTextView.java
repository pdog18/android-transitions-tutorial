package com.pdog18.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.widget.AppCompatTextView;

public class LogTextView extends AppCompatTextView {
    private static final String TAG = "LogTextView";

    public LogTextView(Context context) {
        this(context, null);
    }

    public LogTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        log();
    }

    private void log() {
        Log.e(TAG, "I'm created!"+ this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "I'm onAttachedToWindow!" + this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "I'm onDetachedFromWindow!" + this);
    }
}
