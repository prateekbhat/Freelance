package com.idea_factory.idea_factory.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.activities.SplashInteractor;

/**
 * Created by JITESH on 20-09-2018.
 */
public class ViewCustomDialog {

    public void showInternetDialog(final Context context, String msg, final SplashInteractor mSplashInteractor){
        final Dialog dialog = new Dialog(context);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custoim_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mSplashInteractor.appClose();

            }
        });

        dialog.show();

    }

}
