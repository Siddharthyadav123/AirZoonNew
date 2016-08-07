package com.az.airzoon.volly;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.az.airzoon.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sid on 07/08/2016.
 */
public class APIHandler {

    public static final int RESPONSE_TYPE_JSON_OBJECT = 0;
    public static final int RESPONSE_TYPE_JSON_ARRAY = 1;
    public static final int RESPONSE_TYPE_JSON_STRING = 2;

    private Context context;
    private int requestId;
    private int methodType;
    private int responseType;
    private boolean showLoading = false;
    private String loadingText;
    private String url;

    private ProgressDialog pDialog = null;
    private APICallback apiCallback = null;

    public APIHandler(Context context, APICallback apiCallback, int requestId, int methodType, int responseType, String url,
                      boolean showLoading, String loadingText) {
        this.context = context;
        this.apiCallback = apiCallback;
        this.requestId = requestId;
        this.methodType = methodType;
        this.url = url;
        this.responseType = responseType;
        this.showLoading = showLoading;
        this.loadingText = loadingText;
    }

    private void showLoading() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(loadingText);
        pDialog.show();
    }

    public void requestAPI() {
        System.out.println("[API] request url=" + url);
        if (showLoading) {
            showLoading();
        }

        if (responseType == RESPONSE_TYPE_JSON_OBJECT) {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(methodType,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("[API] response=" + response.toString());
                            if (pDialog != null)
                                pDialog.hide();

                            if (apiCallback != null) {
                                apiCallback.onAPISuccessResponse(requestId, response.toString());
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("[API] error=" + error.getMessage());
                    if (pDialog != null)
                        pDialog.hide();

                    if (apiCallback != null) {
                        apiCallback.onAPIFailureResponse(requestId, error.getMessage());
                    }
                }
            });

            // Adding request to request queue
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, requestId);


        } else if (responseType == RESPONSE_TYPE_JSON_ARRAY) {
            JsonArrayRequest req = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            System.out.println("[API] response=" + response.toString());
                            if (pDialog != null)
                                pDialog.hide();

                            if (apiCallback != null) {
                                apiCallback.onAPISuccessResponse(requestId, response.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("[API] error=" + error.getMessage());
                    if (pDialog != null)
                        pDialog.hide();

                    if (apiCallback != null) {
                        apiCallback.onAPIFailureResponse(requestId, error.getMessage());
                    }
                }
            });
            // Adding request to request queue
            MyApplication.getInstance().addToRequestQueue(req, requestId);
        } else {
            StringRequest strReq = new StringRequest(methodType,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println("[API] response=" + response.toString());

                    if (pDialog != null)
                        pDialog.hide();

                    if (apiCallback != null) {
                        apiCallback.onAPISuccessResponse(requestId, response.toString());
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("[API] error=" + error.getMessage());
                    if (pDialog != null)
                        pDialog.hide();

                    if (apiCallback != null) {
                        apiCallback.onAPIFailureResponse(requestId, error.getMessage());
                    }
                }
            });

            // Adding request to request queue
            MyApplication.getInstance().addToRequestQueue(strReq, requestId);
        }
    }
}
