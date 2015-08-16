package com.divi.tsunapper.client.request;

import com.divi.tsunapper.client.RateDTO;
import com.divi.tsunapper.client.Tsunapper;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class RateRequest extends RetrofitSpiceRequest<Void, Tsunapper> {
	public String providerId;
	public String appPackage;
	public float rating;

	public RateRequest(String providerId, String appPackage, float rating) {
		super(Void.class, Tsunapper.class);
		this.providerId = providerId;
		this.appPackage = appPackage;
		this.rating = rating;
	}

	@Override
	public Void loadDataFromNetwork() throws Exception {
		getService().rate(new RateDTO(providerId, appPackage, rating));
		return null;
	}

}
