package com.idea_factory.idea_factory.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Admin on 03-08-2017.
 */

public class ResponseModel {

    @SerializedName("message")
    String message = "";
    @SerializedName("status")
    String status = "";
    @SerializedName("dataObject")
    JSONObject dataObject = new JSONObject();
    @SerializedName("data")
    Object data;

    @SerializedName("url")
    String url = "";

    public ResponseModel(String message, String status, String url, JSONObject dataObject, Object data) {
        this.message = message;
        this.status = status;
        this.url = url;
        this.dataObject = dataObject;
        this.data = data;
    }

    public ResponseModel(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(JSONObject dataObject) {
        this.dataObject = dataObject;
    }

    public JSONArray getData() {
        return (JSONArray) data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
