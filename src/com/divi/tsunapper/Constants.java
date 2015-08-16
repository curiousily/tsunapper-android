package com.divi.tsunapper;

import com.divi.tsunapper.adapter.NavigationItem;

/**
 * Created on 9/17/13.
 */
public interface Constants {

	public static final int NAVIGATION_MY_APPS = 0;
	public static final int NAVIGATION_RECOMMENDED = 1;

	public static final NavigationItem[] NAVIGATION_ITEMS = {
			new NavigationItem(R.drawable.my_apps, "my apps"),
			new NavigationItem(R.drawable.recommended_apps,
					"recommended for you") };

	public static final String FLURRY_KEY = "KEY HERE";
}
