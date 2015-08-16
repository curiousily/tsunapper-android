package com.divi.tsunapper.client.request;

import com.divi.tsunapper.client.Tsunapper;
import com.divi.tsunapper.model.User;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class SignUpRequest extends RetrofitSpiceRequest<User, Tsunapper> {

	private User user;

	public SignUpRequest(User user) {
		super(User.class, Tsunapper.class);
		this.user = user;
	}

	@Override
	public User loadDataFromNetwork() throws Exception {
		return getService().registerUser(user);
	}

}
