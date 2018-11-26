package com.idea_factory.idea_factory.asynctask;

import android.content.Context;


import com.idea_factory.idea_factory.model.ResponseModel;
import com.idea_factory.idea_factory.utils.Constants;
import com.idea_factory.idea_factory.utils.OkhttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AsyncRequest {

    JSONObject jsonObject = new JSONObject();
    RequestBody requestBody;
    String url = "";
    String requestType = "";
    Context context;
    String token;
    ResponseModel responseModel = null;
    private OnTaskCompleted listener;

    public AsyncRequest(String url, JSONObject jsonObject, String requestType, OnTaskCompleted listener, Context context, String token) {
        this.url = url;
        this.jsonObject = jsonObject;
        this.listener = listener;
        this.requestType = requestType;
        this.context = context;
        this.token = token;
    }

    public AsyncRequest(String url, RequestBody requestBody, String requestType, OnTaskCompleted listener, Context context, String token) {
        this.url = url;
        this.requestBody = requestBody;
        this.listener = listener;
        this.requestType = requestType;
        this.context = context;
        this.token = token;
    }

    public void execute() {
        Request request;
        if (requestType.equals(Constants.REQUESTS.POST.name())) {
            if (requestBody == null) {
                MediaType mediaType = MediaType.parse("application/json");
                requestBody = RequestBody.create(mediaType, jsonObject.toString());
            }
            if (token != null) {
                request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .build();
            }
        } else if (requestType.equals(Constants.REQUESTS.PUT.name())) {
            if (requestBody == null) {
                MediaType mediaType = MediaType.parse("application/json");
                requestBody = RequestBody.create(mediaType, jsonObject.toString());
            }
            if (token != null) {
                request = new Request.Builder()
                        .url(url)
                        .put(requestBody)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .put(requestBody)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .build();
            }
        } else {
            if (token != null) {
                request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .build();
            }
        }
        new OkhttpHelper(context).getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseModel = new ResponseModel(e.getMessage(), "error");
                listener.onTaskCompleted(responseModel);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    responseModel = new ResponseModel(jsonObject.getString("message")
                            , jsonObject.getString("status")
                            , jsonObject.has("url") ? jsonObject.getString("url") : ""
                            , jsonObject.has("dataObject") ? jsonObject.getJSONObject("dataObject") : new JSONObject()
                            , jsonObject.has("data") ? jsonObject.getJSONArray("data") : new JSONArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseBody);
                        responseModel = new ResponseModel(""
                                , ""
                                , ""
                                , jsonObject
                                , new JSONArray());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        try {
                            JSONArray jsonArray = new JSONArray(responseBody);
                            responseModel = new ResponseModel(""
                                    , ""
                                    , ""
                                    , new JSONObject()
                                    , jsonArray);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                //responseModel = new Gson().fromJson(responseBody, ResponseModel.class);
                listener.onTaskCompleted(responseModel);
            }
        });
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(ResponseModel responseModel);
    }

}
