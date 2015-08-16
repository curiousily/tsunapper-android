package com.divi.tsunapper.auth;

import com.divi.tsunapper.model.User;

/**
 * Created on 7/20/13.
 */
public interface Provider {
	public interface GetUserListener {
		void onUser(User user);
	}

	void getUser(GetUserListener user);
}
