package com.az.airzoon.screens;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.az.airzoon.R;
import com.az.airzoon.adapters.SearchResultListAdapter;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.dialog_screens.EditProfileDialog;
import com.az.airzoon.dialog_screens.HotspotDetailDailog;
import com.az.airzoon.dialog_screens.NewHotspotDailog;
import com.az.airzoon.dialog_screens.ProfileDialog;
import com.az.airzoon.listeners.ImageCallback;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.social_integration.FaceBookModel;
import com.az.airzoon.social_integration.ProfilePicLoader;
import com.az.airzoon.social_integration.SocialLoginInterface;
import com.az.airzoon.social_integration.TwitterModel;
import com.az.airzoon.swipe_menu.SwipeMenu;
import com.az.airzoon.swipe_menu.SwipeMenuListView;
import com.az.airzoon.swipe_menu.SwipeMenuView;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;
import com.cocosw.bottomsheet.BottomSheet;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.soundcloud.android.crop.Crop;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import io.fabric.sdk.android.Fabric;

/**
 * Created by sid on 29/07/2016.
 */
public class SearchResultActivity extends Activity implements APICallback {

    public static final String KEY_FILTER_TEXT = "keyFilterText";
    public static final String KEY_FILTER_TYPE = "keyFilterType";

    private ImageView backBtnImageView;
    private Button submitAHotspotButton;
    private SwipeMenuListView hotspotListView;
    private TextView infoText;

    private ArrayList<AirZoonDo> airZoonDoArrayList = new ArrayList<>();
    private SearchResultListAdapter searchResultListAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);
        initViews();
        registerEvents();
        retriveIntentAndDecideSearchList();
        requestTurnGPSOn();

    }

    private void requestTurnGPSOn() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AirZoonMapActivity.REQUEST_LOCATION);
        }
    }


    private void initViews() {
        backBtnImageView = (ImageView) findViewById(R.id.backBtnImageView);
        submitAHotspotButton = (Button) findViewById(R.id.submitAHotspotButton);
        hotspotListView = (SwipeMenuListView) findViewById(R.id.hotspotListView);
        infoText = (TextView) findViewById(R.id.infoText);
    }

    private void registerEvents() {
        backBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitAHotspotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewHotspotDailog newHotspotDailog = new NewHotspotDailog(SearchResultActivity.this);
                newHotspotDailog.showDialog(NewHotspotDailog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
            }
        });

        hotspotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onHotSpotItemClick(position);
            }
        });
        hotspotListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

    }

    private void onShareBtnClick(AirZoonDo airZoonDo) {
        String shareText = this.getString(R.string.shareText) + " \n" + airZoonDo.getName() + " \n" + this.getString(R.string.urlText);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        this.startActivity(sendIntent);
    }


    private void onHotSpotItemClick(int position) {
//        System.out.println("pos>>" + position);
        HotspotDetailDailog hotspotDetailDailog = new HotspotDetailDailog(this, airZoonDoArrayList.get(position));
        hotspotDetailDailog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                searchResultListAdapter.notifyDataSetChanged();
            }
        });
        hotspotDetailDailog.showDialog(HotspotDetailDailog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }

    private void retriveIntentAndDecideSearchList() {
        String filterText = getIntent().getStringExtra(KEY_FILTER_TEXT);
        String hotSpotType = getIntent().getStringExtra(KEY_FILTER_TYPE);

//        System.out.println(">>hotSpotType" + hotSpotType);

        if (filterText != null && filterText.toString().trim().length() > 0) {
            if (hotSpotType != null && hotSpotType.toString().trim().length() > 0) {
                //by search text and type
                airZoonDoArrayList = AirZoonModel.getInstance().getHotSpotListBySearchTextAndType(filterText, hotSpotType);
            } else {
                //by search text only
                airZoonDoArrayList = AirZoonModel.getInstance().getHotSpotListBySearchTextOnly(filterText);
            }
        } else {
            if (hotSpotType != null && hotSpotType.toString().trim().length() > 0) {
                //by type only
                airZoonDoArrayList = AirZoonModel.getInstance().getHotSpotListByType(hotSpotType);
            } else {
                //all list
                airZoonDoArrayList = AirZoonModel.getInstance().getAirZoonDoArrayList();
            }
        }

        //loading list view
        if (airZoonDoArrayList.size() > 0) {
            loadListView();
        } else {
            infoText.setVisibility(View.VISIBLE);
        }

    }


    private void loadListView() {
        searchResultListAdapter = new SearchResultListAdapter(this, airZoonDoArrayList, onMenuItemClickListener);
        hotspotListView.setAdapter(searchResultListAdapter);
    }

    SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener = new SwipeMenuListView.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, SwipeMenuView swipeMenuView, int index) {
            switch (index) {
                case 0:
                    if (!MyApplication.getInstance().isConnectingToInternet()) {
                        MyApplication.getInstance().showNormalDailog(SearchResultActivity.this, getString(R.string.errorcheckInternetConection));
                        break;
                    }
                    if (MyApplication.getInstance().getUserProfileDO().isLoggedInAlrady()) {
                        String fav = "";
                        if (airZoonDoArrayList.get(position).isFaviourate()) {
                            menu.getMenuItems().get(0).setIcon(R.drawable.unselectedstar);
                            airZoonDoArrayList.get(position).setFaviourate(false);
                            fav = "No";
                        } else {
                            menu.getMenuItems().get(0).setIcon(R.drawable.selectedstar);
                            airZoonDoArrayList.get(position).setFaviourate(true);
                            fav = "Yes";
                        }
                        swipeMenuView.refreshFavItem();
                        MyApplication.getInstance().getAirZoonDB().updateFav(airZoonDoArrayList.get(position));

                        //hitting server
                        APIHandler apiHandler = new APIHandler(SearchResultActivity.this, SearchResultActivity.this, RequestConstant.REQUEST_POST_FAVIOURATES,
                                Request.Method.POST, URLConstants.URL_POST_FAVIOURATES, false, null, null,
                                null, airZoonDoArrayList.get(position).getRequestParamsForFav(fav));
                        apiHandler.setShowToastOnRespone(false);
                        apiHandler.requestAPI();

                    } else {

                        MyApplication.getInstance().showAleart(SearchResultActivity.this, dailogCallback, SearchResultActivity.this.getString(R.string.errorText),
                                SearchResultActivity.this.getString(R.string.loginErrorText),
                                SearchResultActivity.this.getString(R.string.signInText),
                                SearchResultActivity.this.getString(R.string.cancelText));

                    }
                    break;
                case 1:
                    onShareBtnClick(airZoonDoArrayList.get(position));
                    break;

            }
            // false : close the menu; true : not close the menu
            return true;
        }
    };

    public MyApplication.DailogCallback dailogCallback = new MyApplication.DailogCallback() {
        @Override
        public void onDailogYesClick() {
            ProfileDialog profileDialog = new ProfileDialog(SearchResultActivity.this);
            profileDialog.showDialog(EditProfileDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
        }

        @Override
        public void onDailogNoClick() {

        }
    };


    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {

    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }


    ////////////bottom sheet////////
    private BottomSheet bottomSheet;
    private ImageCallback imageCallback;

    public void showProfilePicBottomSheet(ImageCallback imageCallback) {
        this.imageCallback = imageCallback;

        bottomSheet = new BottomSheet.Builder(this).title(this.getResources().getString(R.string.selectPictureText))
                .sheet(R.menu.pic_bottom_sheet_menu).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.gallary:
                                galleryIntent();
                                break;
                            case R.id.camera:
                                cameraIntent();
                                break;
                        }
                    }
                }).show();
    }

    boolean isCameraSelected = true;
    private final int SELECT_FILE = 101;

    public static final int REQUEST_CAMERA = 112;
    public static final int REQUEST_GALLARY = 113;

    private void cameraIntent() {
        boolean result = checkPermission(REQUEST_CAMERA, mustPermissions[1], null);
        if (result) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
            isCameraSelected = true;
        }
    }

    private void galleryIntent() {
        boolean result = checkPermission(REQUEST_GALLARY, mustPermissions[2], null);
        if (result) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
            isCameraSelected = false;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            if (requestCode == Crop.REQUEST_CROP) {
                onCropImageResponse(Crop.getOutput(data));
            } else {
                if (fbCallbackManager != null) {
                    fbCallbackManager.onActivityResult(requestCode, resultCode, data);
                }
                if (twitLoginButton != null) {
                    twitLoginButton.onActivityResult(requestCode, resultCode, data);
                }
            }
        } else {
            if (fbCallbackManager != null) {
                fbCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
            if (twitLoginButton != null) {
                twitLoginButton.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void onCropImageResponse(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            File imageFile = new File(imageUri.getPath());
            if (imageCallback != null) {
                imageCallback.onImageFetched(bitmap, imageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri path = data.getData();
        openCropScreenFromGallary(path);
    }

    Uri imagePath = Uri.parse("file:///storage/emulated/0/airZoon/image.jpg");

    private void openCropScreenFromGallary(Uri path) {
        Crop.of(path, imagePath).start(this);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(ProfilePicLoader.IMAGE_AIRZOON_FOLDER + "/image.jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openCropScreenFromGallary(imagePath);
    }


    /**
     * --------------------FB code------------
     */
    private CallbackManager fbCallbackManager = null;
    private FaceBookModel faceBookModel = null;

    public void requestFBLogin(SocialLoginInterface socialLoginInterface) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        fbCallbackManager = CallbackManager.Factory.create();
        faceBookModel = new FaceBookModel(this, socialLoginInterface);
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().registerCallback(fbCallbackManager, faceBookModel);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_status", "user_birthday"));
    }


    /**
     * --------------------Twitter code------------
     */
    private TwitterLoginButton twitLoginButton;

    public void requestTwitterLogin(SocialLoginInterface socialLoginInterface) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(AirZoonMapActivity.TWITTER_KEY, AirZoonMapActivity.TWITTER_SECRET);
        Fabric.with(this, new com.twitter.sdk.android.Twitter(authConfig));
        twitLoginButton = new TwitterLoginButton(this);
        twitLoginButton.setVisibility(View.GONE);
        TwitterModel twitterModel = new TwitterModel(this, socialLoginInterface);
        twitLoginButton.setCallback(twitterModel);
        twitLoginButton.performClick();
    }

    /**
     * -------------------------------------- Permission handling --------------------------------------
     */
    private int permissionRequestCode;
    private Object extras;
    public final int REQUEST_MARSHMELLO_PERMISSIONS = 222;
    boolean haveAllPermissions = false;

    public String[] mustPermissions =
            {
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION

            };

    private boolean havePermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public boolean checkPermission(int requestCode, String permission, Object extras) {
        this.permissionRequestCode = requestCode;
        this.extras = extras;
        //if we have permission then will procceed
        if (havePermission(permission)) {
            return true;
        }
        //else we will take the permission
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(this, "Please provide this permission.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);

        return false;
    }

    public boolean checkPermissions(int requestCode, String[] permission, Object extras) {
        this.permissionRequestCode = requestCode;
        this.extras = extras;
        ArrayList<String> permissionsStr = new ArrayList<>();

        for (int i = 0; i < permission.length; i++) {
            if (!havePermission(permission[i])) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission[i])) {
                    Toast.makeText(this, "Please provide this permission.", Toast.LENGTH_LONG).show();
                }
                permissionsStr.add(permission[i]);
            }
        }


        if (permissionsStr.size() > 0) {
            String[] needTopermissionArray = permissionsStr.toArray(new String[permissionsStr.size()]);
            ActivityCompat.requestPermissions(this, needTopermissionArray, requestCode);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //System.out.println(">>>>>>>>>>>>>>>>> onRequestPermissionsResult " + requestCode + " grantResults " + grantResults.length);
        if (requestCode == permissionRequestCode) {

            if (grantResults.length >= 1) {
                boolean anyDenied = false;
                for (int i = 0; i < grantResults.length; i++) {
                    //System.out.println(">>>>>>>>>>>>>>>> grantResults " + grantResults[i]);
                    // Check if the only required permission has been granted
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "PERMISSION OF " + permissions[i] + " IS GRANTED.", Toast.LENGTH_SHORT).show();


                    } else {
                        anyDenied = true;
//                        Toast.makeText(this, "PERMISSION OF " + permissions[i] + " IS DENIED.", Toast.LENGTH_SHORT).show();
                    }
                }
                if (anyDenied) {
                    haveAllPermissions = false;
                    onPermissionResult(requestCode, false, extras);
                } else {
                    haveAllPermissions = true;
                    onPermissionResult(requestCode, true, extras);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onPermissionResult(int requestCode, boolean isGranted, Object extras) {
        switch (requestCode) {

            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (isGranted) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    cameraIntent();
                }
                return;
            }
            case REQUEST_GALLARY: {
                // If request is cancelled, the result arrays are empty.
                if (isGranted) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    galleryIntent();
                }
                return;
            }
            case MyApplication.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                break;
        }
    }
}
