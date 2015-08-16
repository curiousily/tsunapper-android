package com.divi.tsunapper.auth;

import java.util.List;

import android.content.Context;

import com.divi.tsunapper.model.User;
import com.divi.tsunapper.provider.InstalledAppsProvider;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnFriendsRequestListener;
import com.sromku.simple.fb.SimpleFacebook.OnProfileRequestListener;
import com.sromku.simple.fb.entities.Profile;

/**
 * Created on 7/20/13.
 */
public class FacebookProvider implements Provider {

	public static final String PROVIDER_ID = "facebook";
	private SimpleFacebook simpleFacebook;
	private Context context;

	public FacebookProvider(SimpleFacebook simpleFacebook, Context context) {
		this.simpleFacebook = simpleFacebook;
		this.context = context;
	}

	private OnProfileRequestListener profileListener = new OnProfileRequestListener() {

		@Override
		public void onFail(String reason) {

		}

		@Override
		public void onException(Throwable throwable) {

		}

		@Override
		public void onThinking() {

		}

		@Override
		public void onComplete(Profile profile) {
			final User user = new User();
			user.email = profile.getEmail();
			user.firstName = profile.getFirstName();
			user.lastName = profile.getLastName();
			user.provider = PROVIDER_ID;
			user.providerId = profile.getId();
			simpleFacebook.getFriends(new OnFriendsRequestListener() {

				@Override
				public void onFail(String reason) {

				}

				@Override
				public void onException(Throwable throwable) {

				}

				@Override
				public void onThinking() {

				}

				@Override
				public void onComplete(List<Profile> friends) {
					user.installedApps = InstalledAppsProvider.create(context)
							.getApps();
					for (Profile friend : friends) {
						user.friendIds.add(friend.getId());
					}
					listener.onUser(user);
				}
			});
		}
	};
	private GetUserListener listener;

	@Override
	public void getUser(GetUserListener listener) {
		this.listener = listener;
		simpleFacebook.getProfile(profileListener);
	}

}
