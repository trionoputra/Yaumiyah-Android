package com.yondev.yaumiyah.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ThinkPad on 7/8/2017.
 */

public class WrapListView  extends ListView {

    public WrapListView  (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapListView  (Context context) {
        super(context);
    }

    public WrapListView  (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}