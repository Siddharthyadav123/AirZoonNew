package com.az.airzoon.models;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by sid on 05/08/2016.
 */
public class FontModel {
    public static FontModel fontModel;

    private Typeface architecht;
    private Typeface gothamBook;
    private Typeface gothamBookBold;
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
            architecht = Typeface.createFromAsset(context.getAssets(), "architects_daughter.ttf");
            gothamBook = Typeface.createFromAsset(context.getAssets(), "gotham_book.otf");
            gothamBookBold = Typeface.createFromAsset(context.getAssets(), "gotham_bold.ttf");
            gothamBookLight = Typeface.createFromAsset(context.getAssets(), "gotham_light.otf");
            OpensansBold = Typeface.createFromAsset(context.getAssets(), "opensans_bold.ttf");
            OpensansRegular = Typeface.createFromAsset(context.getAssets(), "opensans_regular.ttf");
            OpensansLight = Typeface.createFromAsset(context.getAssets(), "opensans_light.ttf");
            opensansSemibold = Typeface.createFromAsset(context.getAssets(), "opensans_semibold.ttf");
        } catch (Exception e) {
            try {
                architecht = Typeface.createFromAsset(context.getAssets(), "fonts/architects_daughter.ttf");
                gothamBook = Typeface.createFromAsset(context.getAssets(), "fonts/gotham_book.otf");
                gothamBookBold = Typeface.createFromAsset(context.getAssets(), "fonts/gotham_bold.ttf");
                gothamBookLight = Typeface.createFromAsset(context.getAssets(), "fonts/gotham_light.otf");
                OpensansBold = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_bold.ttf");
                OpensansRegular = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_regular.ttf");
                OpensansLight = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_light.ttf");
                opensansSemibold = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_semibold.ttf");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * <attr name="sid_customFont">
     * <enum name="architech" value="0" />
     * <enum name="gotham_book" value="1" />
     * <enum name="open_sans_bold" value="2" />
     * <enum name="open_sans_light" value="3" />
     * <enum name="open_sans_regular" value="4" />
     * <enum name="gotham_x_light" value="5" />
     * <enum name="open_sans_semibold" value="6" />
     * </attr>
     *
     * @param fontId
     */

    public Typeface getFontById(int fontId, boolean isBold) {
        switch (fontId) {
            case 0:
                if (isBold) {

                }
                return architecht;
            case 1:
                if (isBold) {
                    return gothamBookBold;
                }
                return gothamBook;
            case 2:
                if (isBold) {

                }
                return OpensansBold;
            case 3:
                if (isBold) {
                    return OpensansBold;
                }
                return OpensansLight;
            case 4:
                if (isBold) {
                    return OpensansBold;
                }
                return OpensansRegular;
            case 5:
                if (isBold) {
                    return gothamBookBold;
                }
                return gothamBookLight;
            case 6:
                if (isBold) {
                    return OpensansBold;
                }
                return opensansSemibold;
        }

        return OpensansRegular;
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
