package com.divi.tsunapper;

import android.os.Bundle;

import com.divi.tsunapper.activity.AppListActivity;
import com.divi.tsunapper.client.request.RecommendedAppsRequest;
import com.divi.tsunapper.model.App.AppList;
import com.godapps.appoftheday.AppOfTheDay;
import com.mvptech.shookz.Nagger;
import com.mvptech.shookz.nagscreen.RatingNagger;
import com.octo.android.robospice.request.listener.RequestListener;

public class RecommendAppListActivity extends AppListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Nagger nagger = new Nagger(this);
		nagger.addNagScreen(new RatingNagger(getString(R.string.app_name)));
		nagger.startNagging();
		AppOfTheDay.show(this);
	}

	@Override
	protected void getApps(RequestListener<AppList> listener) {
		spiceManager.execute(new RecommendedAppsRequest(user.providerId),
				listener);
	}

}
