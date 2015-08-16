package com.divi.tsunapper.client;

public class RateDTO {

	public String providerId;
	public String appPackage;
	public float rating;

	public RateDTO(String providerId, String appPackage, float rating) {
		this.providerId = providerId;
		this.appPackage = appPackage;
		this.rating = rating;
	}

}
