package com.idea_factory.idea_factory.utils;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.idea_factory.idea_factory.BuildConfig;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.idea_factory.idea_factory.utils.NetworkUtils.isNetworkConnected;



/**
 * Created by Admin on 03-08-2017.
 */

public class OkhttpHelper {

    OkHttpClient client;

    Context context;

    public OkhttpHelper(Context context) {
        this.context = context;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            if (BuildConfig.debug) {
                client = new OkHttpClient.Builder()
                        .cache(new Cache(context.getCacheDir(), 100 * 1024 * 1024)) // 100 MB
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                if (isNetworkConnected(context)) {
                                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                                } else {
                                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                                }
                                return chain.proceed(request);
                            }
                        })
                        .addNetworkInterceptor(new StethoInterceptor())
                        .build();
            } else {
                client = new OkHttpClient.Builder()
                        .cache(new Cache(context.getCacheDir(), 60 * 1024 * 1024)) // 10 MB
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                if (isNetworkConnected(context)) {
                                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                                } else {
                                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                                }
                                return chain.proceed(request);
                            }
                        })
                        .build();
            }
        }

        return client;
    }
}
