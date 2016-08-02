package com.az.airzoon.dataobjects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sid on 7/26/2016.
 */

public class UserProfileDO {
    private String id;
    private String birthday;
    private String gender;
    private String name;
    private String email;
    private String url;

    public UserProfileDO() {

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

    public void parseJsonDataForLinkedIn(JSONObject object) {
        if (object != null) {
            try {
                if (object.has("emailAddress")) {
                    setEmail((String) object.get("emailAddress"));
                }
                if (object.has("formattedName")) {
                    setName((String) object.get("formattedName"));
                }
                if (object.has("pictureUrls")) {
                    JSONObject pictureUrls = (JSONObject) object.get("pictureUrls");
                    if (pictureUrls.has("values")) {
                        JSONArray jsonArray = (JSONArray) pictureUrls.get("values");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            setUrl((String) jsonArray.get(0));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}