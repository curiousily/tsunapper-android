package com.divi.tsunapper.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.divi.tsunapper.R;

public class ErrorDialog {

	public static void show(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.error_dialog_title)
				.setMessage(R.string.error_dialog_message)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								activity.finish();
							}
						});
		builder.show();
	}
}
