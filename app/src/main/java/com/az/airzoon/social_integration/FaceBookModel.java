package com.az.airzoon.social_integration;

import android.content.Context;
import android.os.Bundle;

import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.Constants;
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
    private SocialLoginInterface socialLoginInterface;

    public FaceBookModel(Context context, SocialLoginInterface socialLoginInterface) {
        this.context = context;
        this.socialLoginInterface = socialLoginInterface;
    }


    @Override
    public void onSuccess(LoginResult loginResult) {
        onLoginSuccess(loginResult);
    }

    @Override
    public void onCancel() {
        if (socialLoginInterface != null)
            socialLoginInterface.onSocialLoginCancel(Constants.LOGIN_TYPE_FB);
    }

    @Override
    public void onError(FacebookException error) {
        if (socialLoginInterface != null)
            socialLoginInterface.onSocialLoginFailure(error.getMessage(), Constants.LOGIN_TYPE_FB);
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
                            UserProfileDO userProfileDO = MyApplication.getInstance().getUserProfileDO();
                            userProfileDO.destroyProfile();
                            userProfileDO.parseJsonDataForFacebook(object);
                            userProfileDO.saveProfile(Constants.LOGIN_TYPE_FB);
                            if (socialLoginInterface != null)
                                socialLoginInterface.onSocialLoginSuccess(userProfileDO, Constants.LOGIN_TYPE_FB);


                        } catch (Exception e) {
                            e.printStackTrace();
                            if (socialLoginInterface != null)
                                socialLoginInterface.onSocialLoginFailure(e.getMessage(), Constants.LOGIN_TYPE_FB);
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,picture,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
