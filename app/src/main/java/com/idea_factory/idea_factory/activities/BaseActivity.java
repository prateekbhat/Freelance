package com.idea_factory.idea_factory.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.prefs.ReadPref;
import com.idea_factory.idea_factory.prefs.SavePref;
import com.idea_factory.idea_factory.widget.Progressbar;


/**
 * Created by Admin on 9/17/2018.
 */

public class BaseActivity  extends AppCompatActivity {

    public ReadPref getReadPref() {
        return new ReadPref(this);
    }

    public SavePref getSavePref() {
        return new SavePref(this);
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    public void showAlert(final String message, final String title, final Activity activity) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alertDialog = new AlertDialog.Builder(BaseActivity.this, R.style.AlertDialogTheme).create();
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(message);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ((DashBoardActivity)activity).showPdf();
                                }
                            });
                    alertDialog.show();
                }
            });
        } catch (Exception e) {
            Log.e("showAlert", "Exception --- > " + e.getMessage());
        }
    }

    public void showAlert(final String message) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alertDialog = new AlertDialog.Builder(BaseActivity.this, R.style.AlertDialogTheme).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage(message);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        } catch (Exception e) {
            Log.e("showAlert", "Exception --- > " + e.getMessage());
        }
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Progressbar progressbar;

    public void showProgressBar() {
        if (progressbar == null)
            progressbar = Progressbar.show(this);
        else if (!progressbar.isShowing()) {
            progressbar = Progressbar.show(this);
        }
    }

    public void showProgressBar(String message) {
        if (progressbar == null)
            progressbar = Progressbar.show(this, message);
        else if (!progressbar.isShowing()) {
            progressbar = Progressbar.show(this, message);
        }
    }

    public void hideProgressBar() {
        try {
            if (progressbar != null) {
                progressbar.dismiss();
                progressbar = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void replaceFragment(Fragment fragment, boolean toPop){
        if(toPop){
            getFragmentManager().popBackStack();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();

    }*/

}
