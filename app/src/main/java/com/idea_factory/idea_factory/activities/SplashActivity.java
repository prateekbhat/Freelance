package com.idea_factory.idea_factory.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.utils.PermissionUtility;
import com.idea_factory.idea_factory.utils.ViewCustomDialog;

import static com.idea_factory.idea_factory.API.Api.GetSubCategories;
import static com.idea_factory.idea_factory.utils.NetworkUtils.isNetworkConnected;


public class SplashActivity extends BaseActivity implements SplashInteractor {
    Context context;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        view = (View) findViewById(R.id.img);

        if (PermissionUtility.checkPermission(context)) {
            nextFunction();
        }

    }

    public void nextFunction() {
        if (isNetworkConnected(context)) {
            GetSubCategories(this, this, SplashActivity.this);
        } else {
            callDialog();
        }
    }

    String[] permissionsToAsk = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtility.MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted :)
                    nextFunction();
                    //downloadFile();
                } else {
                    // permission was not granted
                    if (this == null) {
                        return;
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        showStoragePermissionRationale();
                    } else {
                        Snackbar.make(view, "Storage permission is necessary",
                                Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (this == null) {
                                    return;
                                }
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, 12);
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .show();
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showStoragePermissionRationale() {
        Snackbar.make(view, "Storage permission is necessary",
                Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (this == null) {
                    return;
                }
                ActivityCompat.requestPermissions((Activity)
                                SplashActivity.this, permissionsToAsk,
                        PermissionUtility.MY_PERMISSIONS_REQUEST);
            }
        }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PermissionUtility.checkPermission(context)) {
            nextFunction();
        }

    }


    private void callDialog() {
        ViewCustomDialog mViewCustomDialog = new ViewCustomDialog();
        mViewCustomDialog.showInternetDialog(this, "Application required Internet connection!", this);
    }

    @Override
    public void appClose() {
        finish();
    }

}
