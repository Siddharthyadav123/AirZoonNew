package com.az.airzoon.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.az.airzoon.R;
import com.az.airzoon.models.FontModel;

/**
 * Created by siddharth on 8/16/2016.
 */
public class CustomFontEditText extends EditText {
    private static final String TAG = "EditText";
    public final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontEditText(Context context) {
        super(context);
        setCustomFont(context, null);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFontEditText);
            int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
            final int N = a.getIndexCount();
            for (int i = 0; i < N; ++i) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.CustomFontEditText_sid_customFont:
                        int custom_font = a.getInteger(attr, 0);
                        setCustomFont(ctx, custom_font, textStyle);
                        break;
                }
            }
            a.recycle();
        } else {
            setCustomFont(ctx, 4, 0);
        }

    }


    public boolean setCustomFont(Context ctx, int custom_font, int textStyle) {
        boolean isBold = false;
        if (textStyle == 1) {
            isBold = true;
        }
        setTypeface(FontModel.getInstance().getFontById(custom_font, isBold));
        return true;
    }
}
