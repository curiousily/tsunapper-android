package com.divi.tsunapper.client.request;

import com.divi.tsunapper.client.Tsunapper;
import com.divi.tsunapper.model.User;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class SignInRequest extends RetrofitSpiceRequest<User, Tsunapper> {

	private User user;

	public SignInRequest(User user) {
		super(User.class, Tsunapper.class);
		this.user = user;
	}

	@Override
	public User loadDataFromNetwork() throws Exception {
		return getService().signInUser(user);
	}

}
