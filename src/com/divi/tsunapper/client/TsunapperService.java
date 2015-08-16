package com.divi.tsunapper.client;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class TsunapperService extends RetrofitGsonSpiceService {

	static final String API_URL = "http://tsunapper.herokuapp.com/api/v1";

	@Override
	public void onCreate() {
		super.onCreate();
		addRetrofitInterface(Tsunapper.class);
	}

	@Override
	protected String getServerUrl() {
		return API_URL;
	}

}
