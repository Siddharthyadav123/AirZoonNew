package com.az.airzoon.social_integration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.az.airzoon.dataobjects.UserProfileDO;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by siddharth on 8/3/2016.
 */
public class ProfilePicLoader implements Target {
    private Context context;
    private UserProfileDO userProfileDO;
    private ProgressBar progressBar = null;
    private ImageView imageView;
    public static String IMAGE_AIRZOON_FOLDER = Environment.getExternalStorageDirectory().toString() + "/airZoon";


    public ProfilePicLoader(Context context, UserProfileDO userProfileDO) {
        this.context = context;
        this.userProfileDO = userProfileDO;
    }


    public void downloadProfilePic(ImageView imageView, ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.imageView = imageView;

        Bitmap imageBitmap = getLocalImage(userProfileDO.getName() + ".jpg");

        if (imageBitmap == null) {
            if (userProfileDO.getUrl() != null && userProfileDO.getUrl().length() > 0) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                Picasso.with(context).load(userProfileDO.getUrl()).into(this);
            } else {
                Toast.makeText(context, "Profile Pic not found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (imageView != null) {
                imageView.setImageBitmap(imageBitmap);
            }
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }

    }


    private void saveBitmapInLocal(Bitmap bitmap) {
        try {
            File myDir = new File(IMAGE_AIRZOON_FOLDER);
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            String name = userProfileDO.getName() + ".jpg";
            myDir = new File(myDir, name);
            FileOutputStream out = new FileOutputStream(myDir);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getLocalImage(String fileName) {
        Bitmap bitmap = null;
        File f = new File(IMAGE_AIRZOON_FOLDER + "/" + fileName);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onBitmapLoaded(Bitmap imageBitmap, Picasso.LoadedFrom from) {
        if (imageBitmap == null) {
            if (userProfileDO.getUrl() != null && userProfileDO.getUrl().length() > 0) {
                Picasso.with(context).load(userProfileDO.getUrl()).into(this);
            } else {
                Toast.makeText(context, "Profile Pic not found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (imageView != null) {
                imageView.setImageBitmap(imageBitmap);
            }
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }

        saveBitmapInLocal(imageBitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Toast.makeText(context, "Profile Download failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }


}
