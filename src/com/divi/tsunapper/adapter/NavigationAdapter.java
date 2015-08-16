package com.divi.tsunapper.adapter;

import static com.divi.tsunapper.Constants.NAVIGATION_ITEMS;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.divi.tsunapper.R;
import com.divi.tsunapper.TsunapperApp;

/**
 * Created on 9/17/13.
 */
public class NavigationAdapter extends ArrayAdapter<NavigationItem> {

	public static final int ICON_WIDTH = 50;
	public static final int ICON_HEIGHT = 50;

	public NavigationAdapter(Context context) {
		super(context, R.layout.drawer_list_item, NAVIGATION_ITEMS);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView navigationItemView = (TextView) convertView;

		if (navigationItemView == null) {
			LayoutInflater inflater = ((Activity) getContext())
					.getLayoutInflater();
			navigationItemView = (TextView) inflater.inflate(
					R.layout.drawer_list_item, parent, false);
		}
		NavigationItem navigationItem = NAVIGATION_ITEMS[position];
		navigationItemView.setText(navigationItem.getText());
		navigationItemView.setTypeface(Typeface.createFromAsset(getContext()
				.getAssets(), TsunapperApp.OPENSANS_BOLD));
		Drawable image = getContext().getResources().getDrawable(
				navigationItem.getImage());
		image.setBounds(0, 0, ICON_WIDTH, ICON_HEIGHT);
		navigationItemView.setCompoundDrawables(image, null, null, null);
		return navigationItemView;
	}
}