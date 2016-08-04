package com.az.airzoon.social_integration;

import com.az.airzoon.dataobjects.UserProfileDO;

/**
 * Created by siddharth on 8/2/2016.
 */
public interface SocialLoginInterface {
    public void onSocialLoginSuccess(UserProfileDO facebookDO, String socialType);

    public void onSocialLoginFailure(String error, String socialType);

    public void onSocialLoginCancel(String socialType);
}
