package com.divi.tsunapper;

import android.os.Bundle;

import com.divi.tsunapper.activity.AppListActivity;
import com.divi.tsunapper.client.request.InstalledAppsRequest;
import com.divi.tsunapper.model.App.AppList;
import com.octo.android.robospice.request.listener.RequestListener;

public class InstalledAppListActivity extends AppListActivity {

	@Override
	protected void putExtras(Bundle bundle) {
		super.putExtras(bundle);
		bundle.putBoolean("installedApp", true);
	}

	@Override
	protected void getApps(RequestListener<AppList> listener) {
		spiceManager.execute(new InstalledAppsRequest(user.providerId), listener);
	}
}