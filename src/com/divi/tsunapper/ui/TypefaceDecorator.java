package com.divi.tsunapper.ui;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.divi.tsunapper.TsunapperApp;

public class TypefaceDecorator {
	private Typeface regularTypeface;
	private Typeface boldTypeface;
	private View view;

	public TypefaceDecorator(AssetManager assetManager, View view) {
		this.view = view;
		regularTypeface = Typeface.createFromAsset(assetManager,
				TsunapperApp.OPENSANS_REGULAR);
		boldTypeface = Typeface.createFromAsset(assetManager,
				TsunapperApp.OPENSANS_BOLD);
	}

	public TypefaceDecorator(AssetManager assetManager, Activity activity) {
		this(assetManager, activity.findViewById(android.R.id.content));
	}

	public void decorateRegular(int[] ids) {
		for (int id : ids) {
			TextView view = (TextView) this.view.findViewById(id);
			view.setTypeface(regularTypeface);
		}
	}

	public void decorateBold(int[] ids) {
		for (int id : ids) {
			TextView view = (TextView) this.view.findViewById(id);
			view.setTypeface(boldTypeface);
		}

	}
}
