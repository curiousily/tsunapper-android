package com.divi.tsunapper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.divi.tsunapper.R;
import com.divi.tsunapper.model.App;
import com.divi.tsunapper.model.App.AppList;
import com.divi.tsunapper.ui.TypefaceDecorator;
import com.loopj.android.image.SmartImageView;

public class AppAdapter extends ArrayAdapter<App> implements
		View.OnClickListener {
	private final AppList apps;
	private final OnAppClickListener listener;

	public AppAdapter(Context context, AppList apps, OnAppClickListener listener) {
		super(context, R.layout.app_view, apps);
		this.apps = apps;
		this.listener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View appView = convertView;

		if (appView == null) {
			LayoutInflater inflater = ((Activity) getContext())
					.getLayoutInflater();
			appView = inflater.inflate(R.layout.app_view, parent, false);
			appView.setOnClickListener(this);
		}
		TypefaceDecorator typefaceDecorator = new TypefaceDecorator(
				getContext().getAssets(), appView);
		typefaceDecorator.decorateRegular(new int[] { R.id.app_view_title,
				R.id.app_view_publisher, R.id.app_view_download_text,
				R.id.app_view_screens_label });
		App app = apps.get(position);
		appView.setTag(app);
		SmartImageView appIcon = (SmartImageView) appView
				.findViewById(R.id.app_view_icon);
		appIcon.setImageUrl(app.iconUrl);

		TextView name = (TextView) appView.findViewById(R.id.app_view_title);
		name.setText(app.name);

		TextView publisher = (TextView) appView
				.findViewById(R.id.app_view_publisher);
		publisher.setText("by " + app.publisher);

		return appView;
	}

	@Override
	public void onClick(View view) {
		listener.onClicked((App) view.getTag());
	}
}
