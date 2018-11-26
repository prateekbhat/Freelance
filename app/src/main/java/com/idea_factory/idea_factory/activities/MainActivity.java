package com.idea_factory.idea_factory.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.idea_factory.idea_factory.R;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_dashboard));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

    }


    /*public void login(View view) {

        showProgressBar();

        RequestBody formBody = new FormBody.Builder()
                .add("token", "")
                .build();

        String apiUrl = getReadPref().getStringValue("apiUrl");

        new AsyncRequest(apiUrl + "/user", formBody, Constants.REQUESTS.POST.name(), new AsyncRequest.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ResponseModel responseModel) {

                Log.d(TAG, "" + responseModel);

                hideProgressBar();
                if (responseModel == null) {
                    showAlert("message", "title");
                    return;
                }

                try {
                    JSONObject jsonObject = responseModel.getDataObject();

                    Log.d(TAG, "Json Response------->" + jsonObject.toString());

                    if (!jsonObject.getBoolean("success")) {
                        showAlert("message", "title");
                        return;
                    }

                    getSavePref().saveInt("UserId", jsonObject.getInt("id"));
                    getSavePref().saveString("userName", edtUsername.getText().toString());

                } catch (JSONException e) {
                    showAlert("message", "title");
                    e.printStackTrace();
                }

            }
        }, context, null).execute();

    }*/
}
