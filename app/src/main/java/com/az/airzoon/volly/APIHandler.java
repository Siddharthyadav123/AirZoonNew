package com.az.airzoon.volly;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.az.airzoon.application.MyApplication;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sid on 07/08/2016.
 */
public class APIHandler implements Response.Listener<Object>, Response.ErrorListener {
    private Context context;
    private int requestId;
    private int methodType;
    private boolean showLoading = false;
    private String loadingText;
    private String url;

    private ProgressDialog pDialog = null;
    private APICallback apiCallback = null;
    private String requestBody = null;
    private Map<String, String> headers = null;
    private ArrayList<RequestParam> requestParams = null;

    public APIHandler(Context context, APICallback apiCallback, int requestId, int methodType, String url,
                      boolean showLoading, String loadingText, String requestBody, Map<String, String> headers,
                      ArrayList<RequestParam> requestParams) {
        this.context = context;
        this.apiCallback = apiCallback;
        this.requestId = requestId;
        this.methodType = methodType;
        this.url = url;
        this.showLoading = showLoading;
        this.loadingText = loadingText;
        this.requestBody = requestBody;
        this.headers = headers;
        this.requestParams = requestParams;
    }

    private void showLoading() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(loadingText);
        pDialog.show();
    }

    private void hideLoading() {
        if (pDialog != null)
            pDialog.dismiss();
    }

    public void requestAPI() {
        System.out.println("[API] request url = " + url);
        System.out.println("[API] request body = " + requestBody);
        if (showLoading) {
            showLoading();
        }
        if (methodType == Request.Method.GET) {
            GenericRequest genericRequest = new GenericRequest(methodType, url, requestBody, this, this, null);
            MyApplication.getInstance().addToRequestQueue(genericRequest, requestId);
        } else {
            requestUsingMutipart();
        }

    }

    private void requestUsingMutipart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MultipartUtility multipart = new MultipartUtility(url, "UTF-8");
                    if (requestParams != null) {
                        for (int i = 0; i < requestParams.size(); i++) {
                            //if value is null need to set it as blank
                            if (requestParams.get(i).getValue() == null) {
                                requestParams.get(i).setValue("non");
                            }
                            System.out.println("[API] multipart key = " + requestParams.get(i).getKey() + " >>value = " + requestParams.get(i).getValue());
                            multipart.addFormField(requestParams.get(i).getKey(), requestParams.get(i).getValue());
                        }
                    }
                    String reaponseString = "";
                    List<String> response = multipart.finish();
                    for (String line : response) {
                        reaponseString = reaponseString + line;
                    }
                    onMutipartPostSuccessResponse(reaponseString);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (apiCallback != null) {
                        apiCallback.onAPIFailureResponse(requestId, e.getMessage());
                    }
                    System.out.println("[API] response fail multipart = " + e.getMessage());
                    hideLoading();
                }
            }


        }).start();
    }

    private void onMutipartPostSuccessResponse(String reaponseString) {
        if (reaponseString != null && reaponseString.trim().startsWith("[")) {
            try {
                System.out.println("[API] response body multipart before parsing= " + reaponseString);
                JSONArray jsonArray = new JSONArray(reaponseString);
                reaponseString = jsonArray.get(0).toString();
                System.out.println("[API] response body multipart after parsing= " + reaponseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hideLoading();
        if (apiCallback != null) {
            apiCallback.onAPISuccessResponse(requestId, reaponseString);
        }
    }


    @Override
    public void onResponse(Object response) {
        hideLoading();
        if (response != null) {
            System.out.println("[API] response body volly = " + response.toString());
            if (apiCallback != null) {
                apiCallback.onAPISuccessResponse(requestId, response.toString());
            }
        } else {
            if (apiCallback != null) {
                apiCallback.onAPIFailureResponse(requestId, "Error in response");
            }
            System.out.println("[API] response fail volly = " + "Error in response");
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (apiCallback != null) {
            apiCallback.onAPIFailureResponse(requestId, error.getMessage());
        }
        hideLoading();
    }
}
