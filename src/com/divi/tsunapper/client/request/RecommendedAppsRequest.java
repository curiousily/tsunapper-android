package com.divi.tsunapper.client.request;

import com.divi.tsunapper.client.Tsunapper;
import com.divi.tsunapper.model.App;
import com.divi.tsunapper.model.App.AppList;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class RecommendedAppsRequest extends
		RetrofitSpiceRequest<App.AppList, Tsunapper> {

	private String userId;

	public RecommendedAppsRequest(String userId) {
		super(App.AppList.class, Tsunapper.class);
		this.userId = userId;
	}

	@Override
	public AppList loadDataFromNetwork() throws Exception {
		return getService().getRecommendations(userId);
	}

}
