package com.divi.tsunapper;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.divi.tsunapper.client.TsunapperService;
import com.divi.tsunapper.client.request.AppRequest;
import com.divi.tsunapper.client.request.RateRequest;
import com.divi.tsunapper.model.App;
import com.divi.tsunapper.publisher.FacebookPublisher;
import com.divi.tsunapper.ui.ErrorDialog;
import com.divi.tsunapper.ui.LoadingDialog;
import com.divi.tsunapper.ui.TypefaceDecorator;
import com.loopj.android.image.SmartImageView;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.sromku.simple.fb.SimpleFacebook;

public class AppDetailActivity extends Activity implements
		View.OnClickListener, FacebookPublisher.PulishListener,
		OnRatingBarChangeListener {

	private App app;
	private NumberFormat numberFormat;
	private SpiceManager spiceManager = new SpiceManager(TsunapperService.class);
	private SimpleFacebook simpleFacebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_detail);
		simpleFacebook = SimpleFacebook.getInstance(this);
		TypefaceDecorator typefaceDecorator = new TypefaceDecorator(
				getAssets(), this);
		typefaceDecorator
				.decorateRegular(new int[] { R.id.detail_app_title,
						R.id.detail_app_publisher, R.id.detail_install_button,
						R.id.detail_uninstall_button, R.id.detail_open_button,
						R.id.detail_market_rating_text,
						R.id.detail_app_market_downloads,
						R.id.detail_app_description });
		typefaceDecorator.decorateBold(new int[] {
				R.id.detail_user_rating_label,
				R.id.detail_app_description_label });

		numberFormat = NumberFormat.getInstance(Locale.getDefault());

		String appPackage = getIntent().getStringExtra("appPackage");

		String providerId = ((TsunapperApp) getApplication()).getUser().providerId;

		LoadingDialog.show(this);

		spiceManager.execute(new AppRequest(appPackage, providerId),
				new RequestListener<App>() {

					@Override
					public void onRequestFailure(SpiceException arg0) {
						LoadingDialog.hide();
						ErrorDialog.show(AppDetailActivity.this);
					}

					@Override
					public void onRequestSuccess(App requestApp) {
						LoadingDialog.hide();
						app = requestApp;
						showApp();
					}
				});

	}

	private void addImages() {
		ViewGroup view = (ViewGroup) findViewById(R.id.detail_gallery);
		for (int i = 0; i < app.images.size() && i < view.getChildCount(); i++) {
			SmartImageView child = (SmartImageView) view.getChildAt(i);
			String imageUrl = app.images.get(i);
			child.setImageUrl(imageUrl);
		}
	}

	private Spannable createMarketRatingText() {
		Spannable spannable = new SpannableString("(" + app.marketRating.rating
				+ " rated by "
				+ numberFormat.format(app.marketRating.rateCount) + ")");

		spannable.setSpan(
				new ForegroundColorSpan(getResources().getColor(R.color.Blue)),
				1, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannable.setSpan(
				new ForegroundColorSpan(getResources().getColor(
						R.color.LightGray)), 4, 14,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannable.setSpan(
				new ForegroundColorSpan(getResources().getColor(R.color.Blue)),
				14, spannable.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}

	private void installApp() {
		Intent marketIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("market://details?id=" + app.appPackage));
		marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
				| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		startActivity(marketIntent);
		publishInstall();
	}

	private void publishInstall() {
		FacebookPublisher publisher = new FacebookPublisher(simpleFacebook);
		publisher
				.publish(
						"New app: " + app.name,
						"New app: " + app.name,
						app.name + " installed via Tsunapper",
						"Tsunapper gives you recommendations for the best apps for your Android phone. Completely free!",
						"https://play.google.com/store/apps/details?id=com.divi.tsunapper",
						"", this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_install_button:
			installApp();
			break;
		case R.id.detail_uninstall_button:
			uninstallApp();
			break;
		case R.id.detail_open_button:
			openApp();
			break;
		}
	}

	private void openApp() {
		PackageManager manager = getPackageManager();
		try {
			Intent intent = manager.getLaunchIntentForPackage(app.appPackage);
			if (intent == null) {
				throw new PackageManager.NameNotFoundException();
			}
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			startActivity(intent);
		} catch (PackageManager.NameNotFoundException e) {

		}
	}

	private void uninstallApp() {
		Uri packageURI = Uri.parse("package:" + app.appPackage);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		startActivity(uninstallIntent);
	}

	@Override
	public void onRatingChanged(RatingBar bar, float rating, boolean fromUser) {
		if (fromUser) {
			String providerId = ((TsunapperApp) getApplication()).getUser().providerId;
			spiceManager.execute(new RateRequest(providerId, app.appPackage,
					rating), new RequestListener<Void>() {

				@Override
				public void onRequestFailure(SpiceException arg0) {
					Toast.makeText(getApplicationContext(),
							"Rating failed. Try again later!",
							Toast.LENGTH_LONG).show();
				}

				@Override
				public void onRequestSuccess(Void arg0) {
					Toast.makeText(getApplicationContext(),
							"You've rated " + app.name + ". Thank you!",
							Toast.LENGTH_LONG).show();
				}
			});
			bar.setIsIndicator(true);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		simpleFacebook = SimpleFacebook.getInstance(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		simpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

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
	public void onPublished() {
		Toast.makeText(this, "Published", Toast.LENGTH_LONG).show();
	}

	private void showApp() {
		boolean installedApp = getIntent().getBooleanExtra("installedApp",
				false);
		SmartImageView appIcon = (SmartImageView) findViewById(R.id.detail_app_icon);

		appIcon.setImageUrl(app.iconUrl);
		TextView appTitle = (TextView) findViewById(R.id.detail_app_title);
		appTitle.setText(app.name);
		TextView appPublisher = (TextView) findViewById(R.id.detail_app_publisher);
		appPublisher.setText("by " + app.publisher);
		TextView marketDownloads = (TextView) findViewById(R.id.detail_app_market_downloads);
		TextView marketRating = (TextView) findViewById(R.id.detail_market_rating_text);

		marketRating.setText(createMarketRatingText());

		RatingBar marketRatingBar = (RatingBar) findViewById(R.id.detail_market_rating);
		marketRatingBar.setIsIndicator(true);
		marketRatingBar.setRating(app.marketRating.rating);

		TextView appDescription = (TextView) findViewById(R.id.detail_app_description);
		appDescription.setText(Html.fromHtml(app.description));

		RatingBar userRatingBar = (RatingBar) findViewById(R.id.detail_user_rating_bar);

		if (!installedApp) {
			findViewById(R.id.detail_app_user_rating).setVisibility(View.GONE);
			findViewById(R.id.detail_market_rating_separator).setVisibility(
					View.GONE);
			findViewById(R.id.detail_open_button).setVisibility(View.GONE);
			findViewById(R.id.detail_uninstall_button).setVisibility(View.GONE);
			findViewById(R.id.detail_install_button).setOnClickListener(this);
		} else {
			findViewById(R.id.detail_install_button).setVisibility(View.GONE);
			findViewById(R.id.detail_open_button).setOnClickListener(this);
			findViewById(R.id.detail_uninstall_button).setOnClickListener(this);
		}

		if (app.isRated()) {
			userRatingBar.setIsIndicator(true);
			userRatingBar.setRating(app.rating);
			TextView userRatingLabel = (TextView) findViewById(R.id.detail_user_rating_label);
			userRatingLabel.setText("your rating");
		} else {
			userRatingBar.setOnRatingBarChangeListener(this);
		}

		String lowRange = numberFormat.format(app.downloadCount.lowRange);
		String highRange = numberFormat.format(app.downloadCount.highRange);

		Spannable spannable = new SpannableString(lowRange + " - " + highRange
				+ " downloads");
		spannable.setSpan(
				new ForegroundColorSpan(getResources().getColor(
						R.color.LightGray)), spannable.length() - 9,
				spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		marketDownloads.setText(spannable);

		addImages();
	}

}
