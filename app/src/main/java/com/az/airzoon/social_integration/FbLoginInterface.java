package com.az.airzoon.social_integration;

import com.az.airzoon.dataobjects.UserProfileDO;

/**
 * Created by siddharth on 8/2/2016.
 */
public interface FbLoginInterface {
    public void onFbLoginSuccess(UserProfileDO facebookDO);

    public void onFbLoginFailure(String error);

    public void onFbLoginCancel();
}
