package com.az.airzoon.social_integration;

import android.content.Context;
import android.widget.Toast;

import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.dataobjects.UserProfileDO;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;


/**
 * Created by siddharth on 8/4/2016.
 */
public class TwitterModel extends Callback<TwitterSession> {
    private static Context context;
    private SocialLoginInterface socialLoginInterface;

    public TwitterModel(Context context, SocialLoginInterface socialLoginInterface) {
        this.context = context;
        this.socialLoginInterface = socialLoginInterface;
    }


    @Override
    public void success(Result result) {
        Toast.makeText(context, result.data.toString(), Toast.LENGTH_SHORT).show();

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;
//        Preferences preferences = Preferences.getInstance(context);
//        preferences.saveStringInPreference(Preferences.KEY_TWITTER_AUTH_TOKEN, token);
//        preferences.saveStringInPreference(Preferences.KEY_TWITTER_SECRET, secret);
//        preferences.saveBooleanInPreference(Preferences.KEY_TWITTER_LOGIN, true);
        getUserData(session);
    }

    @Override
    public void failure(TwitterException e) {
        if (socialLoginInterface != null)
            socialLoginInterface.onSocialLoginFailure(e.getMessage(), Constants.LOGIN_TYPE_TWITTER);
    }

    /**
     * Gets the Twitter user profile Data
     *
     * @param session
     */
    private void getUserData(TwitterSession session) {

        final UserProfileDO userProfileDO = MyApplication.getInstance().getUserProfileDO();
        userProfileDO.destroyProfile();
        userProfileDO.setToken(session.getAuthToken().token);
        userProfileDO.setFbid(session.getUserId() + "");
//        System.out.println(">>user name" + session.getUserName());

        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {

                        String imageUrl = userResult.data.profileImageUrlHttps;
                        if (imageUrl != null && imageUrl.contains("_normal")) {
                            imageUrl = imageUrl.replace("_normal", "");
                        }
//                        System.out.println(">>twitter image uri=" + imageUrl);


                        userProfileDO.setUrl(imageUrl);
                        userProfileDO.setEmail(userResult.data.email);
                        userProfileDO.setName(userResult.data.name);
                        userProfileDO.saveProfile(Constants.LOGIN_TYPE_TWITTER);

                        if (socialLoginInterface != null)
                            socialLoginInterface.onSocialLoginSuccess(userProfileDO, Constants.LOGIN_TYPE_TWITTER);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        if (socialLoginInterface != null)
                            socialLoginInterface.onSocialLoginFailure(e.getMessage(), Constants.LOGIN_TYPE_TWITTER);
                    }

                });
    }


}
