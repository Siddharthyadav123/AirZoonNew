package com.az.airzoon.social_integration;

import android.content.Context;
import android.os.Bundle;

import com.az.airzoon.dataobjects.UserProfileDO;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

/**
 * Created by siddharth on 8/2/2016.
 */
public class FaceBookModel implements FacebookCallback<LoginResult> {
    private Context context;
    private FbLoginInterface fbLoginInterface;

    public FaceBookModel(Context context, FbLoginInterface fbLoginInterface) {
        this.context = context;
        this.fbLoginInterface = fbLoginInterface;
    }


    @Override
    public void onSuccess(LoginResult loginResult) {
        onLoginSuccess(loginResult);
    }

    @Override
    public void onCancel() {
        if (fbLoginInterface != null)
            fbLoginInterface.onFbLoginCancel();
    }

    @Override
    public void onError(FacebookException error) {
        if (fbLoginInterface != null)
            fbLoginInterface.onFbLoginFailure(error.getMessage());
    }

    /**
     * Get called when Twitter login get success
     *
     * @param loginResult
     */
    private void onLoginSuccess(LoginResult loginResult) {
//        instance.saveStringInPreference(Preferences.KEY_FACEBOOK_AUTH_TOKEN, loginResult.getAccessToken().getToken());
//        instance.saveStringInPreference(Preferences.KEY_FB_USER_ID, loginResult.getAccessToken().getUserId());
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            UserProfileDO facebookDO = new UserProfileDO();
                            facebookDO.parseJsonDataForFacebook(object);

                            if (fbLoginInterface != null)
                                fbLoginInterface.onFbLoginSuccess(facebookDO);


                        } catch (Exception e) {
                            e.printStackTrace();
                            if (fbLoginInterface != null)
                                fbLoginInterface.onFbLoginFailure(e.getMessage());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,picture,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
