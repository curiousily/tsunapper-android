package com.divi.tsunapper.model;

import java.util.ArrayList;
import java.util.List;

public class App {
	public String id;
	public String iconUrl;
	public String name;
	public String url;
	public String publisher;
	public List<String> images;
	public String description;
	public MarketRating marketRating;
	public DownloadCount downloadCount;
	public String appPackage;
	public float rating;

	public boolean isRated() {
		return rating > 0.0f;
	}

	@SuppressWarnings("serial")
	public static class AppList extends ArrayList<App> {
	}
}
