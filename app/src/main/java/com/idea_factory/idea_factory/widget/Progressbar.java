package com.idea_factory.idea_factory.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.idea_factory.idea_factory.R;


public class Progressbar extends Dialog {

	public Progressbar(Context context) {
		super(context);
	
		
	}
	public Progressbar(Context context, int theme) {
		super(context,theme);
	
		
	}

	public static Progressbar show(Context context) {
		Progressbar dialog = new Progressbar(context, R.style.Theme_MyprogressDialog);
		dialog.setTitle("");
		dialog.setContentView(R.layout.progress_hud);
		dialog.setCancelable(false);
		dialog.getWindow().getAttributes().gravity= Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount=0.2f;
		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		dialog.show();
		return dialog;
	}

    public static Progressbar show(Context context, String message) {

		Progressbar dialog = new Progressbar(context, R.style.Theme_MyprogressDialog);
        dialog.setTitle("");
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_hud, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        TextView tvMessage = view.findViewById(R.id.tvMessage);

        tvMessage.setText(message);

        dialog.getWindow().getAttributes().gravity= Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}
	
}
