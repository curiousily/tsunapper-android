package com.divi.tsunapper.activity;

import static com.divi.tsunapper.Constants.NAVIGATION_MY_APPS;
import static com.divi.tsunapper.Constants.NAVIGATION_RECOMMENDED;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.divi.tsunapper.AppDetailActivity;
import com.divi.tsunapper.Constants;
import com.divi.tsunapper.InstalledAppListActivity;
import com.divi.tsunapper.R;
import com.divi.tsunapper.RecommendAppListActivity;
import com.divi.tsunapper.TsunapperApp;
import com.divi.tsunapper.adapter.AppAdapter;
import com.divi.tsunapper.adapter.NavigationAdapter;
import com.divi.tsunapper.adapter.OnAppClickListener;
import com.divi.tsunapper.client.TsunapperService;
import com.divi.tsunapper.model.App;
import com.divi.tsunapper.model.App.AppList;
import com.divi.tsunapper.model.User;
import com.divi.tsunapper.ui.ErrorDialog;
import com.divi.tsunapper.ui.LoadingDialog;
import com.divi.tsunapper.ui.TypefaceDecorator;
import com.facebook.widget.ProfilePictureView;
import com.flurry.android.FlurryAgent;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

public abstract class AppListActivity extends SherlockActivity implements
		AdapterView.OnItemClickListener {

	private ListView appList;
	private SherlockActionBarDrawerToggle toggle;

	private DrawerLayout drawerLayout;
	private ListView navigationList;
	protected User user;
	private Bundle extras = new Bundle();

	protected SpiceManager spiceManager = new SpiceManager(
			TsunapperService.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_list_layout);

		TypefaceDecorator typefaceDecorator = new TypefaceDecorator(
				getAssets(), this);
		typefaceDecorator.decorateRegular(new int[] { R.id.drawer_username,
				R.id.drawer_user_email });
		typefaceDecorator.decorateBold(new int[] { R.id.drawer_account_label,
				R.id.drawer_navigation_label });

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		user = ((TsunapperApp) getApplication()).getUser();

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navigationList = (ListView) findViewById(R.id.drawer_navigation_list);
		navigationList.setOnItemClickListener(this);
		navigationList.setAdapter(new NavigationAdapter(this));

		toggle = new SherlockActionBarDrawerToggle(this, drawerLayout,
				R.drawable.drawer_icon, R.string.app_name, R.string.rate_label) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle("Tsunapper");
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle("MY PROFILE");
			}
		};
		drawerLayout.setDrawerListener(toggle);
		putExtras(extras);
		showProfileInfo();
		showApps();
	}

	protected void putExtras(Bundle bundle) {

	}

	private void showApps() {
		appList = (ListView) findViewById(R.id.app_list);
		LoadingDialog.show(this);
		getApps(new RequestListener<App.AppList>() {

			@Override
			public void onRequestFailure(SpiceException arg0) {
				LoadingDialog.hide();
				ErrorDialog.show(AppListActivity.this);
			}

			@Override
			public void onRequestSuccess(AppList apps) {
				LoadingDialog.hide();
				AppAdapter adapter = new AppAdapter(AppListActivity.this, apps,
						new OnAppClickListener() {
							@Override
							public void onClicked(App app) {

								Intent intent = new Intent(
										AppListActivity.this,
										AppDetailActivity.class);

								extras.putString("appPackage", app.appPackage);
								intent.putExtras(extras);
								startActivity(intent);
							}
						});
				appList.setAdapter(adapter);
			}
		});

	}

	protected abstract void getApps(RequestListener<AppList> listener);

	private void showProfileInfo() {
		TextView username = (TextView) findViewById(R.id.drawer_username);
		username.setText(user.firstName + " " + user.lastName);
		TextView email = (TextView) findViewById(R.id.drawer_user_email);
		email.setText(user.email);

		ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.drawer_profile_pic);
		profilePictureView.setProfileId(user.providerId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (toggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long l) {
		switch (position) {
		case NAVIGATION_MY_APPS:
			startActivity(new Intent(this, InstalledAppListActivity.class));
			finish();
			break;

		case NAVIGATION_RECOMMENDED:
			startActivity(new Intent(this, RecommendAppListActivity.class));
			finish();
			break;
		}
	}

	@Override
	protected void onStart() {
		spiceManager.start(this);
		super.onStart();
		FlurryAgent.onStartSession(this, Constants.FLURRY_KEY);
	}

	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

}
