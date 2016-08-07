package com.az.airzoon.volly;

/**
 * Created by sid on 07/08/2016.
 */
public interface APICallback {
    public void onAPISuccessResponse(int requestId, String responseString);

    public void onAPIFailureResponse(int requestId, String errorString);
}
