package com.az.airzoon.models;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by sid on 05/08/2016.
 */
public class FontModel {
    public static FontModel fontModel;

    private Typeface gothamBook;
    private Typeface gothamBookLight;
    private Typeface OpensansBold;
    private Typeface OpensansRegular;
    private Typeface OpensansLight;
    private Typeface opensansSemibold;

    private FontModel() {

    }

    public static FontModel getInstance() {
        if (fontModel == null) {
            fontModel = new FontModel();
        }
        return fontModel;
    }

    public void loadFonts(Context context) {
        try {
            gothamBook = Typeface.createFromAsset(context.getAssets(), "Gotham-Book.ttf");
            gothamBookLight = Typeface.createFromAsset(context.getAssets(), "Gotham-XLight.ttf");
            OpensansBold = Typeface.createFromAsset(context.getAssets(), "OpenSans-Bold.ttf");
            OpensansRegular = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
            OpensansLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
            opensansSemibold = Typeface.createFromAsset(context.getAssets(), "OpenSans-Semibold.ttf");
        } catch (Exception e) {
            try {
                gothamBook = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Book.ttf");
                gothamBookLight = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-XLight.ttf");
                OpensansBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf");
                OpensansRegular = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
                OpensansLight = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf");
                opensansSemibold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Semibold.ttf");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }


    public Typeface getGothamBook() {
        return gothamBook;
    }

    public Typeface getGothamBookLight() {
        return gothamBookLight;
    }

    public Typeface getOpensansBold() {
        return OpensansBold;
    }

    public Typeface getOpensansRegular() {
        return OpensansRegular;
    }

    public Typeface getOpensansLight() {
        return OpensansLight;
    }

    public Typeface getOpensansSemibold() {
        return opensansSemibold;
    }
}
