package com.az.airzoon.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.az.airzoon.application.MyApplication;

/**
 * Created by siddharth on 8/22/2016.
 */
public class CurveRelLayout extends RelativeLayout {
    public float radius = 10f;
    private Context context;

    public CurveRelLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CurveRelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CurveRelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        radius = MyApplication.getInstance().convertDpToPixel(10, context);
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
