package com.divi.tsunapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.divi.tsunapper.auth.FacebookProvider;
import com.divi.tsunapper.auth.Provider;
import com.divi.tsunapper.auth.Provider.GetUserListener;
import com.divi.tsunapper.client.ConnectionChecker;
import com.divi.tsunapper.client.TsunapperService;
import com.divi.tsunapper.client.request.SignInRequest;
import com.divi.tsunapper.client.request.SignUpRequest;
import com.divi.tsunapper.model.User;
import com.divi.tsunapper.ui.ErrorDialog;
import com.divi.tsunapper.ui.LoadingDialog;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;

public class SplashActivity extends Activity {

	private SimpleFacebook simpleFacebook;

	private SpiceManager spiceManager = new SpiceManager(TsunapperService.class);

	private final class SignUpRequestListener implements RequestListener<User> {
		private final User user;

		private SignUpRequestListener(User user) {
			this.user = user;
		}

		@Override
		public void onRequestFailure(SpiceException arg0) {
			spiceManager.execute(new SignUpRequest(user),
					new RequestListener<User>() {

						@Override
						public void onRequestFailure(SpiceException arg0) {
							LoadingDialog.hide();
							ErrorDialog.show(SplashActivity.this);
						}

						@Override
						public void onRequestSuccess(User arg0) {
							LoadingDialog.hide();
							startApp();
						}
					});
		}

		@Override
		public void onRequestSuccess(User arg0) {
			startApp();
		}
	}

	private OnLoginListener loginListener = new OnLoginListener() {

		@Override
		public void onFail(String reason) {
			LoadingDialog.hide();
			ErrorDialog.show(SplashActivity.this);
		}

		@Override
		public void onException(Throwable throwable) {
			LoadingDialog.hide();
			ErrorDialog.show(SplashActivity.this);
		}

		@Override
		public void onThinking() {

		}

		@Override
		public void onNotAcceptingPermissions() {
			ErrorDialog.show(SplashActivity.this);
		}

		@Override
		public void onLogin() {
			loginUser();
		}

	};

	private Provider provider;

	@Override
	protected void onStart() {
		spiceManager.start(this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ConnectionChecker.isNetworkAvailable(this)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.no_internet_dialog_title)
					.setMessage(R.string.no_internet_dialog_message)
					.setCancelable(false)
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
								}
							});
			builder.show();
			return;
		}
		LoadingDialog.show(this);
		simpleFacebook = SimpleFacebook.getInstance(this);
		provider = new FacebookProvider(simpleFacebook, this);
		simpleFacebook.login(loginListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		simpleFacebook = SimpleFacebook.getInstance(this);
		provider = new FacebookProvider(simpleFacebook, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		simpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startApp() {
		startActivity(new Intent(this, RecommendAppListActivity.class));
		finish();
	}

	private void loginUser() {
		provider.getUser(new GetUserListener() {

			@Override
			public void onUser(User user) {
				TsunapperApp app = (TsunapperApp) getApplication();
				app.setUser(user);
				spiceManager.execute(new SignInRequest(user),
						new SignUpRequestListener(user));
			}
		});
	}

}
