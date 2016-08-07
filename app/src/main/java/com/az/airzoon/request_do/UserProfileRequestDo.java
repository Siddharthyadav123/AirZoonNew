package com.az.airzoon.request_do;

import org.json.JSONObject;

/**
 * Created by sid on 07/08/2016.
 * <p/>New user:
 * name, fbid, gender, email, phoneno, token, profile_pic
 * <p/>
 * <p/>Edit user:
 * name, gender, email, phone, token, profile_pic, acess_token, user_id
 */
public class UserProfileRequestDo {
    public String user_id;
    public String name;
    public String fbid;
    public String gender;
    public String email;
    public String phoneno;
    public String phone;
    public String token;
    public String profile_pic;
    public String acess_token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getAcess_token() {
        return acess_token;
    }

    public void setAcess_token(String acess_token) {
        this.acess_token = acess_token;
    }

    /**
     * * <p/>New user:
     * name, fbid, gender, email, phoneno, token, profile_pic
     */
    public String formJSONforNewUser() {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", getName());
            jsonObject.put("fbid", getFbid());
            jsonObject.put("gender", getGender());
            jsonObject.put("email", getEmail());
            jsonObject.put("phoneno", getPhoneno());
            jsonObject.put("token", getToken());
            jsonObject.put("profile_pic", getProfile_pic());

            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * <p/>Edit user:
     * name, gender, email, phone, token, profile_pic, acess_token, user_id
     */
    public String formJSONtoEditUser() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", getUser_id());
            jsonObject.put("name", getName());
            jsonObject.put("fbid", getFbid());
            jsonObject.put("gender", getGender());
            jsonObject.put("email", getEmail());
            jsonObject.put("phone", getPhone());
            jsonObject.put("token", getToken());
            jsonObject.put("profile_pic", getProfile_pic());
            jsonObject.put("acess_token", getAcess_token());

            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
