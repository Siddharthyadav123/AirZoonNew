package com.az.airzoon.dataobjects;

import android.content.Context;

import com.az.airzoon.application.MyApplication;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.preferences.PrefManager;
import com.az.airzoon.social_integration.ProfilePicLoader;
import com.az.airzoon.volly.RequestParam;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sid on 7/26/2016.
 */

public class UserProfileDO extends BaseModel {

    private final String KEY_ID = "key_id";
    private final String KEY_BIRTHDAY = "key_birth";
    private final String KEY_GENDER = "key_gender";
    private final String KEY_NAME = "key_name";
    private final String KEY_EMAIL = "key_email";
    private final String KEY_URL = "key_url";
    private final String KEY_PH_NO = "kye_ph_no";
    private final String KEY_LOGIN_TYPE = "key_login_type";
    private final String KEY_FBID = "key_fbid";
    private final String KEY_KEY_TOKEN = "key_token";
    private final String KEY_ACCESS_TOKEN = "key_access_token";
    private final String KEY_PROFILE_PIC = "key_profile_pic";

    private String id;
    private String birthday;
    private String gender;
    private String name;
    private String email;
    private String url;
    private String phoneNum;
    private String loginType;
    private String fbid;
    private String token;
    private String acess_token;
    private String profile_pic;
    private String phone;
//    private String fb_id;

    private Context context;
    private PrefManager prefManager;


    public UserProfileDO(Context context) {
        this.context = context;
        prefManager = new PrefManager(context);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAcess_token() {
        return acess_token;
    }

    public void setAcess_token(String acess_token) {
        this.acess_token = acess_token;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void parseJsonDataForFacebook(JSONObject object) {
        if (object != null) {
            try {
                if (object.has("id")) {
                    setFbid((String) object.get("id"));
                }
                if (object.has("birthday")) {
                    setBirthday((String) object.get("birthday"));
                }
                if (object.has("gender")) {
                    setGender((String) object.get("gender"));
                }
                if (object.has("name")) {
                    setName((String) object.get("name"));
                }
                if (object.has("email")) {
                    setEmail((String) object.get("email"));
                }
                if (object.has("picture")) {
                    setUrl("https://graph.facebook.com/" + getFbid() + "/picture?width=300&height=300");
//                    JSONObject pictureJsonObject = (JSONObject) object.get("picture");
//                    if (pictureJsonObject.has("data")) {
//                        JSONObject dataJsonObject = (JSONObject) pictureJsonObject.get("data");
//                        if (dataJsonObject.has("url")) {
//
//                            setUrl((String) dataJsonObject.get("url"));
//                        }
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void loadProfile() {
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }

        setLoginType(prefManager.getPref().getString(KEY_LOGIN_TYPE, ""));
        setId(prefManager.getPref().getString(KEY_ID, ""));
        setBirthday(prefManager.getPref().getString(KEY_BIRTHDAY, ""));
        setGender(prefManager.getPref().getString(KEY_GENDER, ""));
        setName(prefManager.getPref().getString(KEY_NAME, ""));
        setEmail(prefManager.getPref().getString(KEY_EMAIL, ""));
        setUrl(prefManager.getPref().getString(KEY_URL, ""));
        setPhoneNum(prefManager.getPref().getString(KEY_PH_NO, ""));

        setFbid(prefManager.getPref().getString(KEY_FBID, ""));
        setToken(prefManager.getPref().getString(KEY_KEY_TOKEN, ""));
        setAcess_token(prefManager.getPref().getString(KEY_ACCESS_TOKEN, ""));
        setProfile_pic(prefManager.getPref().getString(KEY_PROFILE_PIC, ""));
    }


    public void saveProfile(String loginType) {
        this.loginType = loginType;
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }
        prefManager.getEditor().putString(KEY_LOGIN_TYPE, loginType);
        prefManager.getEditor().putString(KEY_ID, id);
        prefManager.getEditor().putString(KEY_BIRTHDAY, birthday);
        prefManager.getEditor().putString(KEY_GENDER, gender);
        prefManager.getEditor().putString(KEY_NAME, name);
        prefManager.getEditor().putString(KEY_EMAIL, email);
        prefManager.getEditor().putString(KEY_URL, url);
        prefManager.getEditor().putString(KEY_PH_NO, phoneNum);

        prefManager.getEditor().putString(KEY_FBID, fbid);
        prefManager.getEditor().putString(KEY_KEY_TOKEN, token);
        prefManager.getEditor().putString(KEY_ACCESS_TOKEN, acess_token);
        prefManager.getEditor().putString(KEY_PROFILE_PIC, profile_pic);
        prefManager.getEditor().commit();
    }

    public void destroyProfile() {
        setLoginType(null);
        setId(null);
        setBirthday(null);
        setGender(null);
        setName(null);
        setEmail(null);
        setUrl(null);
        setPhoneNum(null);
        setFbid(null);
        setToken(null);
        setAcess_token(null);
        setProfile_pic(null);
        phone = null;

        prefManager.getEditor().putString(KEY_LOGIN_TYPE, loginType);
        prefManager.getEditor().putString(KEY_ID, id);
        prefManager.getEditor().putString(KEY_BIRTHDAY, birthday);
        prefManager.getEditor().putString(KEY_GENDER, gender);
        prefManager.getEditor().putString(KEY_NAME, name);
        prefManager.getEditor().putString(KEY_EMAIL, email);
        prefManager.getEditor().putString(KEY_URL, url);
        prefManager.getEditor().putString(KEY_PH_NO, phoneNum);

        prefManager.getEditor().putString(KEY_FBID, fbid);
        prefManager.getEditor().putString(KEY_KEY_TOKEN, token);
        prefManager.getEditor().putString(KEY_ACCESS_TOKEN, acess_token);
        prefManager.getEditor().putString(KEY_PROFILE_PIC, profile_pic);
        prefManager.getEditor().commit();
        deleteProfilePic();

        //removing marked favs.
        AirZoonModel.getInstance().removeFavirates();
        MyApplication.getInstance().getAirZoonDB().removeAllFav();
    }

    private void deleteProfilePic() {
        try {
            File myDir = new File(ProfilePicLoader.IMAGE_AIRZOON_FOLDER);
            if (myDir.exists()) {
                String[] myFiles = myDir.list();
                for (int i = 0; i < myFiles.length; i++) {
                    File myFile = new File(myDir, myFiles[i]);
                    myFile.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoggedInAlrady() {
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }
        String userType = prefManager.getPref().getString(KEY_LOGIN_TYPE, "");
        if (userType != null && userType.trim().length() > 0) {
            return true;
        }
        return false;
    }


    /**
     * Parameters = name, fbid, gender, email, phoneno, token, profile_pic
     *
     * @return
     */
    public ArrayList<RequestParam> getRequestParamsToRegisterUser() {
        ArrayList<RequestParam> requestParams = new ArrayList<>();
        requestParams.add(new RequestParam("name", getName()));
        requestParams.add(new RequestParam("fbid", getFbid()));
        requestParams.add(new RequestParam("gender", getGender()));
        requestParams.add(new RequestParam("email", getEmail()));
        requestParams.add(new RequestParam("token", getToken()));
        requestParams.add(new RequestParam("phoneno", getPhone()));
        requestParams.add(new RequestParam("profile_pic", getUrl()));
        return requestParams;
    }

    /**
     * name, gender, email, phone, token, profile_pic, acess_token, user_id
     *
     * @return
     */
    public ArrayList<RequestParam> getRequestParamsToUpdateUserProfile() {
        ArrayList<RequestParam> requestParams = new ArrayList<>();
        requestParams.add(new RequestParam("name", getName()));
        requestParams.add(new RequestParam("gender", getGender()));
        requestParams.add(new RequestParam("email", getEmail()));
        requestParams.add(new RequestParam("phone", getPhoneNum()));
        requestParams.add(new RequestParam("token", getToken()));
        requestParams.add(new RequestParam("fbid", getFbid()));
        requestParams.add(new RequestParam("profile_pic", getUrl()));
        requestParams.add(new RequestParam("acess_token", getAcess_token()));
        requestParams.add(new RequestParam("user_id", getId()));
        return requestParams;
    }


}