package com.az.airzoon.request_do;

import org.json.JSONObject;

/**
 * Created by sid on 07/08/2016.
 */
public class SendFeedbackRequestDo {

    public String feedback;
    public String email;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String formJSONToPostFeedback() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("feedback", getFeedback());
            jsonObject.put("email", getEmail());
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
