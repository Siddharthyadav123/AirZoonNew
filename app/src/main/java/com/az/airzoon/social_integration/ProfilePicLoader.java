package com.az.airzoon.social_integration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
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


    //for profile pic
    public ProfilePicLoader(Context context, UserProfileDO userProfileDO) {
        this.context = context;
        this.userProfileDO = userProfileDO;
    }

    //for airzoon frame
    public ProfilePicLoader(Context context) {
        this.context = context;
    }

    public void downloadProfilePic(final ImageView imageView, final ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.imageView = imageView;

        final Bitmap imageBitmap = getLocalImage(userProfileDO.getName() + ".png");

        if (imageBitmap == null) {
            if (userProfileDO.getUrl() != null && userProfileDO.getUrl().length() > 0) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
//                Picasso.with(context).load(userProfileDO.getUrl()).into(this);

                ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
                imageLoader.get(userProfileDO.getUrl(), new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onBitmapFailed(null);
                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                        onBitmapLoaded(response.getBitmap(), null);
                    }
                });


            } else {
                Toast.makeText(context, context.getResources().getString(R.string.profilePicNotFoungText), Toast.LENGTH_SHORT).show();

            }
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (imageView != null) {
                        imageView.setImageBitmap(imageBitmap);
                    }
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

        }

    }

    public void downloadAirzoonFrame(final ImageView imageView, String url, String airZoonId) {
        try {
            this.imageView = imageView;

            String fileName = url.substring(url.lastIndexOf('/') + 1);
            if (fileName.contains(".")) {
                fileName = fileName.split("\\.")[0];
            }
            final String airZoonFrameName = "airZoonBgImage_" + fileName + "_" + airZoonId;
            final Bitmap imageBitmap = getLocalImage(airZoonFrameName + ".png");

            if (imageBitmap == null) {
                if (url != null && url.length() > 0) {

                    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
                    imageLoader.get(url, new ImageLoader.ImageListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }

                        @Override
                        public void onResponse(final ImageLoader.ImageContainer response, boolean arg1) {
                            try {
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (imageView != null) {
                                            imageView.setImageBitmap(response.getBitmap());
                                        }
                                    }
                                });
                                File myDir = new File(IMAGE_AIRZOON_FOLDER);
                                if (!myDir.exists()) {
                                    myDir.mkdirs();
                                }


                                String name = airZoonFrameName + ".png";
                                myDir = new File(myDir, name);
                                FileOutputStream out = new FileOutputStream(myDir);
                                response.getBitmap().compress(Bitmap.CompressFormat.PNG, 0, out);
                                out.flush();
                                out.close();

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (imageView != null) {
                                            imageView.setImageBitmap(response.getBitmap());
                                        }
                                    }
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }
            } else {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imageView != null) {
                            imageView.setImageBitmap(imageBitmap);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveBitmapInLocal(final Bitmap bitmap) {
        try {
            File myDir = new File(IMAGE_AIRZOON_FOLDER);
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            String name = userProfileDO.getName() + ".png";
            myDir = new File(myDir, name);
            FileOutputStream out = new FileOutputStream(myDir);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
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
    public void onBitmapLoaded(final Bitmap imageBitmap, Picasso.LoadedFrom from) {
        if (imageBitmap == null) {
            if (userProfileDO.getUrl() != null && userProfileDO.getUrl().length() > 0) {
                Picasso.with(context).load(userProfileDO.getUrl()).into(this);
            } else {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getResources().getString(R.string.profilePicNotFoungText), Toast.LENGTH_SHORT).show();
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (imageView != null) {
                        imageView.setImageBitmap(imageBitmap);
                    }
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

        saveBitmapInLocal(imageBitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }


}
