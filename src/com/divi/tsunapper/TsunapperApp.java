package com.divi.tsunapper;

import android.app.Application;

import com.divi.tsunapper.model.User;
import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

public class TsunapperApp extends Application {
	private User user;

	public static final String OPENSANS_REGULAR = "OpenSans-Regular.ttf";
	public static final String OPENSANS_BOLD = "OpenSans-Bold.ttf";

	private static final String APP_ID = "ID HERE";
	private static final String APP_NAMESPACE = "NS HERE";
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		Permissions[] permissions = new Permissions[] { 
				Permissions.BASIC_INFO,
				Permissions.EMAIL, 
				Permissions.PUBLISH_ACTION,
				Permissions.PUBLISH_STREAM 
		};

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(APP_ID).setNamespace(APP_NAMESPACE)
				.setPermissions(permissions)
				.setDefaultAudience(SessionDefaultAudience.EVERYONE).build();

		SimpleFacebook.setConfiguration(configuration);
	}

}
