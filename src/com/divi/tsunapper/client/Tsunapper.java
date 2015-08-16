package com.divi.tsunapper.client;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.divi.tsunapper.model.App;
import com.divi.tsunapper.model.App.AppList;
import com.divi.tsunapper.model.User;

/**
 * Created on 7/18/13.
 */
public interface Tsunapper {

	@GET("/apps/recommend/{userId}")
	AppList getRecommendations(@Path("userId") String userId);

	@GET("/apps/installed/{userId}")
	AppList getInstalledApps(@Path("userId") String userId);

	@GET("/apps/show/{appPackage}/{providerId}")
	App getApp(@Path("appPackage") String appId,
			@Path("providerId") String providerId);

	@PUT("/apps/rate")
	void rate(@Body RateDTO rate);

	@POST("/users")
	User registerUser(@Body User providerUser);

	@POST("/users/sign-in")
	User signInUser(@Body User providerUser);

}
