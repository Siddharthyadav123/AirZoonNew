package com.az.airzoon.volly;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.az.airzoon.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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

    public APIHandler(Context context, APICallback apiCallback, int requestId, int methodType, String url,
                      boolean showLoading, String loadingText, String requestBody, Map<String, String> headers) {
        this.context = context;
        this.apiCallback = apiCallback;
        this.requestId = requestId;
        this.methodType = methodType;
        this.url = url;
        this.showLoading = showLoading;
        this.loadingText = loadingText;
        this.requestBody = requestBody;
        this.headers = headers;
    }

    private void showLoading() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(loadingText);
        pDialog.show();
    }

    public void requestAPI() {
        System.out.println("[API] request url=" + url);
        System.out.println("[API] request body=" + requestBody);
        if (showLoading) {
            showLoading();
        }
        GenericRequest genericRequest = new GenericRequest(methodType, url, requestBody, this, this, null);
        MyApplication.getInstance().addToRequestQueue(genericRequest, requestId);
    }


    @Override
    public void onResponse(Object response) {
        System.out.println(">>response 22>>" + response);
        if (response != null) {
            if (apiCallback != null) {
                apiCallback.onAPISuccessResponse(requestId, response.toString());
            }
        } else {
            if (apiCallback != null) {
                apiCallback.onAPIFailureResponse(requestId, "Error in response");
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (apiCallback != null) {
            apiCallback.onAPIFailureResponse(requestId, error.getMessage());
        }
    }
}
