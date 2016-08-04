package com.az.airzoon.dataobjects;

import android.content.Context;

import com.az.airzoon.preferences.PrefManager;
import com.az.airzoon.social_integration.ProfilePicLoader;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by sid on 7/26/2016.
 */

public class UserProfileDO {

    private final String KEY_ID = "key_id";
    private final String KEY_BIRTHDAY = "key_birth";
    private final String KEY_GENDER = "key_gender";
    private final String KEY_NAME = "key_name";
    private final String KEY_EMAIL = "key_email";
    private final String KEY_URL = "key_url";
    private final String KEY_PH_NO = "kye_ph_no";
    private final String KEY_LOGIN_TYPE = "key_login_type";

    private String id;
    private String birthday;
    private String gender;
    private String name;
    private String email;
    private String url;
    private String phoneNum;
    private String loginType;

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

    public void parseJsonDataForFacebook(JSONObject object) {
        if (object != null) {
            try {
                if (object.has("id")) {
                    setId((String) object.get("id"));
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

                    JSONObject pictureJsonObject = (JSONObject) object.get("picture");
                    if (pictureJsonObject.has("data")) {
                        JSONObject dataJsonObject = (JSONObject) pictureJsonObject.get("data");
                        if (dataJsonObject.has("url")) {
                            setUrl((String) dataJsonObject.get("url"));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void loadProfile() {
        setLoginType(prefManager.getPref().getString(KEY_LOGIN_TYPE, ""));
        setId(prefManager.getPref().getString(KEY_ID, ""));
        setBirthday(prefManager.getPref().getString(KEY_BIRTHDAY, ""));
        setGender(prefManager.getPref().getString(KEY_GENDER, ""));
        setName(prefManager.getPref().getString(KEY_NAME, ""));
        setEmail(prefManager.getPref().getString(KEY_EMAIL, ""));
        setUrl(prefManager.getPref().getString(KEY_URL, ""));
        setPhoneNum(prefManager.getPref().getString(KEY_PH_NO, ""));
    }

    public void saveProfile(String loginType) {
        this.loginType = loginType;
        prefManager.getEditor().putString(KEY_LOGIN_TYPE, loginType);
        prefManager.getEditor().putString(KEY_ID, id);
        prefManager.getEditor().putString(KEY_BIRTHDAY, birthday);
        prefManager.getEditor().putString(KEY_GENDER, gender);
        prefManager.getEditor().putString(KEY_NAME, name);
        prefManager.getEditor().putString(KEY_EMAIL, email);
        prefManager.getEditor().putString(KEY_URL, url);
        prefManager.getEditor().putString(KEY_PH_NO, phoneNum);
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

        prefManager.getEditor().putString(KEY_LOGIN_TYPE, loginType);
        prefManager.getEditor().putString(KEY_ID, id);
        prefManager.getEditor().putString(KEY_BIRTHDAY, birthday);
        prefManager.getEditor().putString(KEY_GENDER, gender);
        prefManager.getEditor().putString(KEY_NAME, name);
        prefManager.getEditor().putString(KEY_EMAIL, email);
        prefManager.getEditor().putString(KEY_URL, url);
        prefManager.getEditor().putString(KEY_PH_NO, phoneNum);
        prefManager.getEditor().commit();

        deleteProfilePic();
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
        String userType = prefManager.getPref().getString(KEY_LOGIN_TYPE, "");
        if (userType != null && userType.trim().length() > 0) {
            return true;
        }
        return false;
    }

}