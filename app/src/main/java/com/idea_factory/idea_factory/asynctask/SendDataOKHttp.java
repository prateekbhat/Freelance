package com.idea_factory.idea_factory.asynctask;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.idea_factory.idea_factory.model.ResponseModel;
import com.idea_factory.idea_factory.utils.OkhttpHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendDataOKHttp {

    ResponseModel responseModel = null;

    String TAG = "SendDataOKHttp";

    Context context;

    public SendDataOKHttp(Context context) {
        this.context = context;
    }

    public ResponseModel postData(final String url, RequestBody formBody, String token) {

        Request request;
        if (token != null) {
            request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();
        }

        try {
            Response response = new OkhttpHelper(context).getClient().newCall(request).execute();
            responseModel = new Gson().fromJson(response.body().toString(), ResponseModel.class);

        } catch (IOException e) {
            //e.printStackTrace();
            Log.e("SendDataOkHttp", "IOException --- >  " + e.getMessage());
        }
        return responseModel;
    }

    public ResponseModel putData(final String url, RequestBody formBody, String token) {

        Request request;
        if (token != null) {
            request = new Request.Builder()
                    .url(url)
                    .put(formBody)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .put(formBody)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();
        }

        try {
            Response response = new OkhttpHelper(context).getClient().newCall(request).execute();
            responseModel = new Gson().fromJson(response.body().toString(), ResponseModel.class);

        } catch (IOException e) {
            //e.printStackTrace();
            Log.e("SendDataOkHttp", "IOException --- >  " + e.getMessage());
        }
        return responseModel;
    }

    public ResponseModel getData(final String url, String token) {

        Log.d(TAG, "URL - > " + url);

        Request request;

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

        try {
            Response response = new OkhttpHelper(context).getClient().newCall(request).execute();
            responseModel = new Gson().fromJson(response.body().toString(), ResponseModel.class);

        } catch (IOException e) {
            //e.printStackTrace();
            Log.e("SendDataOkHttp", "IOException --- >  " + e.getMessage());
        }
        return responseModel;
    }

}