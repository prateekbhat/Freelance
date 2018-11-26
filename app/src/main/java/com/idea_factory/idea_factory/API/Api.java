package com.idea_factory.idea_factory.API;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.idea_factory.idea_factory.activities.BaseActivity;
import com.idea_factory.idea_factory.activities.DashBoardActivity;
import com.idea_factory.idea_factory.activities.SplashActivity;
import com.idea_factory.idea_factory.activities.SplashInteractor;
import com.idea_factory.idea_factory.asynctask.AsyncRequest;
import com.idea_factory.idea_factory.model.ProductModel;
import com.idea_factory.idea_factory.model.ResponseModel;
import com.idea_factory.idea_factory.model.SubCategoriesModel;
import com.idea_factory.idea_factory.utils.Constants;
import com.idea_factory.idea_factory.utils.GeneralUtils;
import com.idea_factory.idea_factory.widget.Progressbar;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import static android.content.ContentValues.TAG;
import static com.idea_factory.idea_factory.utils.Constants.BASE_URL;
import static com.idea_factory.idea_factory.utils.Constants.arrayListProduct;
import static com.idea_factory.idea_factory.utils.Constants.arrayListSubCategory;

public class Api  {

    public static void GetProductList(final Activity act, final Context ctx) {
        //((BaseActivity)getActivity()).showProgressBar();
        GeneralUtils.showProgressBar(ctx);
        RequestBody formBody = new FormBody.Builder()
                .build();
        new AsyncRequest(BASE_URL + "products.json", formBody, Constants.REQUESTS.GET.name(), new AsyncRequest.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ResponseModel responseModel) {

                Log.d("TAG", "" + responseModel);

                GeneralUtils.hideProgressBar();
                if (responseModel == null) {
                    GeneralUtils.showAlert(act,ctx,"Alert", "Technical error");
                    return;
                }

                try {
                    JSONArray jsonArray = responseModel.getData();

                    Log.d("TAG", "Json Response------->" + jsonArray.toString());

                    arrayListProduct = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        ProductModel productModel = new Gson().fromJson(jsonArray.get(i).toString(), ProductModel.class);
                        arrayListProduct.add(productModel);
                    }

                } catch (JSONException e) {
                    GeneralUtils.showAlert(act,ctx,"Alert", "Technical error");
                    e.printStackTrace();
                }
            }
        }, ctx, null).execute();

    }


    public static void GetSubCategories(final Activity act, final Context ctx, final SplashInteractor mSplashInteractor) {
        //((BaseActivity)getActivity()).showProgressBar();
        GeneralUtils.showProgressBar(ctx);
       /* Progressbar progressbar = null;
        if (progressbar == null)
            progressbar = Progressbar.show(ctx);
        else if (!progressbar.isShowing()) {
            progressbar = Progressbar.show(ctx);
        }*/
        RequestBody formBody = new FormBody.Builder().build();
        new AsyncRequest(BASE_URL + "sub_category.json", formBody, Constants.REQUESTS.GET.name(), new AsyncRequest.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ResponseModel responseModel) {

                Log.d(TAG, "" + responseModel);

                if (responseModel == null) {
                    GeneralUtils.showAlert(act,ctx,"Alert", "Technical error");
                    return;
                }

                try {
                    JSONArray jsonArray = responseModel.getData();

                    Log.d(TAG, "Json Response------->" + jsonArray.toString());

                    arrayListSubCategory = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        SubCategoriesModel subCategories_Model = new Gson().fromJson(jsonArray.get(i).toString(), SubCategoriesModel.class);
                        arrayListSubCategory.add(subCategories_Model);
                    }

                    if (mSplashInteractor!=null && arrayListSubCategory.size() != 0) {

                        ctx.startActivity(new Intent(ctx, DashBoardActivity.class));
                        mSplashInteractor.appClose();
                        //GetProductList();
                    }else if(mSplashInteractor==null){
                        GeneralUtils.hideProgressBar();
                        return;
                    } else {
                        Toast.makeText(ctx, "No product found.", Toast.LENGTH_LONG).show();
                    }
                    GeneralUtils.hideProgressBar();
                } catch (JSONException e) {
                    GeneralUtils.showAlert(act,ctx,"Alert", "Technical error");
                    e.printStackTrace();
                }

            }
        }, ctx, null).execute();
    }
}
