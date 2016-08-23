package com.az.airzoon.customviews;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;

/**
 * Created by siddharth on 8/22/2016.
 */
public class CurveRelLayout extends RelativeLayout {

    /**
     * Used locally to tag Logs
     */
    @SuppressWarnings("unused")
    private static final String TAG = CurveRelLayout.class.getSimpleName();
    private final Path mPath = new Path();
    private Paint paint = new Paint();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CurveRelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    public CurveRelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurveRelLayout(Context context) {
        super(context);
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.reset();
        float round = getResources().getDimension(R.dimen.roundEdges);
        mPath.addRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom()), round, round, Path.Direction.CW);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(mPath);
        super.dispatchDraw(canvas);
    }

}
