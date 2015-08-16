package com.divi.tsunapper.provider;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class InstalledAppsProvider {

	private Context context;

	private InstalledAppsProvider(Context context) {
		this.context = context;
	}

	public static InstalledAppsProvider create(Context context) {
		return new InstalledAppsProvider(context);
	}

	private boolean isSystemPackage(ApplicationInfo applicationInfo) {
		return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}

	public List<String> getApps() {
		final PackageManager pm = context.getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		List<String> installedApps = new ArrayList<String>();
		for (ApplicationInfo packageInfo : packages) {
			if (!isSystemPackage(packageInfo)
					&& !packageInfo.packageName.startsWith("com.android.")) {
				installedApps.add(packageInfo.packageName);
			}
		}
		return installedApps;
	}

}
