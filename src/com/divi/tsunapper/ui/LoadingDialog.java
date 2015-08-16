package com.divi.tsunapper.ui;

import android.app.ProgressDialog;
import android.content.Context;
import com.divi.tsunapper.R;

public class LoadingDialog {

	private static ProgressDialog dialog;

	public static void show(Context context) {
		dialog = ProgressDialog
				.show(context,
						context.getString(R.string.loading_dialog_title),
						context.getString(R.string.loading_dialog_message),
						true, false);
	}

	public static void show(Context context, int messageResource) {
		dialog = ProgressDialog.show(context,
				context.getString(R.string.loading_dialog_title),
				context.getString(messageResource), true, false);
	}

	public static void hide() {
		dialog.dismiss();
	}
}
