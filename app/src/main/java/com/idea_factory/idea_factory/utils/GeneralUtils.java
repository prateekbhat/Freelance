package com.idea_factory.idea_factory.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.widget.Progressbar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Admin on 7/9/2018.
 */

public class GeneralUtils {
    AppCompatActivity activity;
    Context context;
    public static Progressbar progressbar;

    public GeneralUtils(AppCompatActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
    }

    public void moveFragmentForDashBoard(Fragment fragment, String backStack, int containerId) {
        try {
            if (fragment != null) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(containerId, fragment);
                if (backStack != null)
                    fragmentTransaction.addToBackStack(backStack);


                /*if (backStack != null)
                    */
                fragmentTransaction.commit();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void OnClickShare(Context context, ArrayList<ImageView> imagesArrayList) {

        ArrayList<Uri> files = new ArrayList<Uri>();
        for (int i = 0; i < imagesArrayList.size(); i++) {
            Bitmap bitmap = getBitmapFromView(imagesArrayList.get(i));
            try {
                File file = new File(context.getExternalCacheDir(),
                        imagesArrayList.get(i).toString().substring(0, 5) + "_" + i + "_Img.png");
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                file.setReadable(true, false);

                Uri uri = Uri.fromFile(file);

                if(Build.VERSION.SDK_INT > M){
                    uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() +
                            ".my.package.name.provider", file);
                }

                files.add(uri);
            } catch (Exception e) {

                return;
            }
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        intent.setType("image/jpeg"); /* This example is sharing jpeg images. */
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        context.startActivity(Intent.createChooser(intent, "Share image via"));
    }

    private static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public static void DownloadFile(Context ctx, String Url) {
        File directory, mypath;
        DownloadManager downloadManager = (DownloadManager) ctx.getSystemService(DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse(Url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(ctx.getString(R.string.app_name) + "Downloading");
        request.setDescription(ctx.getString(R.string.app_name) + "E-Catalogue");
        request.setVisibleInDownloadsUi(true);


        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "E-Catalogue" + ctx.getString(R.string.app_name) + ".pdf");

        long refid = downloadManager.enqueue(request);
        Log.e("sdsd", String.valueOf(refid));
        directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        mypath = new File(directory, "E-Catalogue_" + ctx.getString(R.string.app_name) + ".pdf");
        if (mypath.exists()) {
            PDF_viewer(mypath, ctx);
        }
    }

    public static void PDF_viewer(File PDFpath, Context context) {
        Uri path = Uri.fromFile(PDFpath);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(pdfIntent, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void showAlert(final Activity act, final Context ctx, final String message, final String title) {
        try {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alertDialog = new AlertDialog.Builder(ctx, R.style.AlertDialogTheme).create();
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(message);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, ctx.getString(R.string.ok),
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


    public static void showProgressBar(Context ctx) {
        if (progressbar == null)
            progressbar = Progressbar.show(ctx);
        else if (!progressbar.isShowing()) {
            progressbar = Progressbar.show(ctx);
        }
    }

    public static void showProgressBar(String message, Context ctx) {
        if (progressbar == null)
            progressbar = Progressbar.show(ctx, message);
        else if (!progressbar.isShowing()) {
            progressbar = Progressbar.show(ctx, message);
        }
    }

    public static void hideProgressBar() {
        try {
            if (progressbar != null) {
                progressbar.dismiss();
                progressbar = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
