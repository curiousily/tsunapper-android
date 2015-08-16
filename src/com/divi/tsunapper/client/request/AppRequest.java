package com.divi.tsunapper.client.request;

import com.divi.tsunapper.client.Tsunapper;
import com.divi.tsunapper.model.App;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class AppRequest extends RetrofitSpiceRequest<App, Tsunapper> {

	private String appId;
	private String providerId;

	public AppRequest(String appId, String providerId) {
		super(App.class, Tsunapper.class);
		this.appId = appId;
		this.providerId = providerId;
	}

	@Override
	public App loadDataFromNetwork() throws Exception {
		return getService().getApp(appId, providerId);
	}

}
