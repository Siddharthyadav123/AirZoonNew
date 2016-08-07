package com.az.airzoon.request_do;

import org.json.JSONObject;

/**
 * Created by sid on 07/08/2016.
 * <p/>
 * <p/>
 * <p/>
 * hotspot, comment, err_name, err_address,
 * err_category, err_hours, err_phone, err_speed, req_name, req_address,
 * req_category, req_hours, req_phone, req_speed,
 */
public class ReportRequestDo {
    public String hotspot;
    public String comment;
    public boolean err_name;
    public boolean err_address;
    public boolean err_category;
    public boolean err_hours;
    public boolean err_phone;
    public boolean err_speed;
    public boolean req_name;
    public boolean req_address;
    public boolean req_category;
    public boolean req_hours;
    public boolean req_phone;
    public boolean req_speed;

    public void setHotspot(String hotspot) {
        this.hotspot = hotspot;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setErr_name(boolean err_name) {
        this.err_name = err_name;
    }

    public void setErr_address(boolean err_address) {
        this.err_address = err_address;
    }

    public void setErr_category(boolean err_category) {
        this.err_category = err_category;
    }

    public void setErr_hours(boolean err_hours) {
        this.err_hours = err_hours;
    }

    public void setErr_phone(boolean err_phone) {
        this.err_phone = err_phone;
    }

    public void setErr_speed(boolean err_speed) {
        this.err_speed = err_speed;
    }

    public void setReq_name(boolean req_name) {
        this.req_name = req_name;
    }

    public void setReq_address(boolean req_address) {
        this.req_address = req_address;
    }

    public void setReq_category(boolean req_category) {
        this.req_category = req_category;
    }

    public void setReq_hours(boolean req_hours) {
        this.req_hours = req_hours;
    }

    public void setReq_phone(boolean req_phone) {
        this.req_phone = req_phone;
    }

    public void setReq_speed(boolean req_speed) {
        this.req_speed = req_speed;
    }

    /**
     * * <p/>
     * <p/>
     * hotspot, comment, err_name, err_address,
     * err_category, err_hours, err_phone, err_speed, req_name, req_address,
     * req_category, req_hours, req_phone, req_speed,
     *
     * @return
     */
    public String formJSONToPostFeedback() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hotspot", hotspot);
            jsonObject.put("comment", comment);
            jsonObject.put("err_name", err_name);
            jsonObject.put("err_address", err_address);
            jsonObject.put("err_category", err_category);
            jsonObject.put("err_hours", err_hours);
            jsonObject.put("err_phone", err_phone);
            jsonObject.put("err_speed", err_speed);
            jsonObject.put("req_name", req_name);
            jsonObject.put("req_address", req_address);
            jsonObject.put("req_category", req_category);
            jsonObject.put("req_hours", req_hours);
            jsonObject.put("req_phone", req_phone);
            jsonObject.put("req_speed", req_speed);

            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
