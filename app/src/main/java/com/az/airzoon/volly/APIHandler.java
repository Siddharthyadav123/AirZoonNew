package com.az.airzoon.volly;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private boolean showToastOnRespone = true;

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
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pDialog = new ProgressDialog(context);
                    pDialog.setMessage(loadingText);
                    pDialog.show();
                }
            });
        }
    }

    private void hideLoading() {
        try {
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pDialog != null)
                            pDialog.dismiss();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void requestAPI() {
        //check if internet connect found or not
        if (!MyApplication.getInstance().checkConnection(context)) {
            if (showToastOnRespone) {
                String noInternetConnection = context.getResources().getString(R.string.errorcheckInternetConection);
                if (apiCallback != null) {
                    apiCallback.onAPIFailureResponse(requestId, noInternetConnection);
                }

                MyApplication.getInstance().showNormalDailog(context, noInternetConnection);
            }
            return;
        }

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
                                requestParams.get(i).setValue("");
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

                    final String finalReaponseString = reaponseString;

                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onMutipartPostSuccessResponse(finalReaponseString);
                            }
                        });
                    }


                } catch (final Exception e) {
                    e.printStackTrace();

                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (apiCallback != null) {
                                    apiCallback.onAPIFailureResponse(requestId, e.getMessage());
                                }
                            }
                        });
                    }
                    System.out.println("[API] response fail multipart = " + e.getMessage());
                    hideLoading();
                }
            }


        }).start();
    }

    private void onMutipartPostSuccessResponse(String reaponseString) {
        String errorMessage = null;
        String responseMessage = null;


        if (reaponseString != null && reaponseString.trim().startsWith("[")) {
            try {
                System.out.println("[API] response body multipart before parsing= " + reaponseString);
                JSONArray jsonArray = new JSONArray(reaponseString);
                reaponseString = jsonArray.get(0).toString();
                System.out.println("[API] response body multipart after parsing= " + reaponseString);

                if (reaponseString != null) {
                    JSONObject jsonObject = new JSONObject(reaponseString);
                    if (jsonObject.length() == 2) {
                        if (jsonObject.has("sucess")) {
                            if (jsonObject.getBoolean("sucess")) {
                                responseMessage = jsonObject.get("message").toString();
                            } else {
                                errorMessage = jsonObject.get("message").toString();
                            }
                        } else {
                            errorMessage = jsonObject.get("message").toString();
                        }
                    } else {
                        responseMessage = jsonObject.get("message").toString();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (apiCallback != null) {
                    apiCallback.onAPIFailureResponse(requestId, e.getMessage());
                }
            }
        }
        hideLoading();

        if (errorMessage == null) {
//            showToast(responseMessage);
            if (apiCallback != null) {
                apiCallback.onAPISuccessResponse(requestId, reaponseString);
            }
        } else {
            showToast(errorMessage);
            if (apiCallback != null) {
                apiCallback.onAPIFailureResponse(requestId, errorMessage);
            }
        }

    }

    private void showToast(final String text) {
        if (showToastOnRespone) {
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (text != null && text.length() > 0)
                            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                    }
                });
            }
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


    public boolean isShowToastOnRespone() {
        return showToastOnRespone;
    }

    public void setShowToastOnRespone(boolean showToastOnRespone) {
        this.showToastOnRespone = showToastOnRespone;
    }
}
