package com.az.airzoon.listeners;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by siddharth on 8/20/2016.
 */
public interface ImageCallback {
    public void onImageFetched(Bitmap bitmap, File imageFile);
}
