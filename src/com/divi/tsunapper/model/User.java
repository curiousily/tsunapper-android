package com.divi.tsunapper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/18/13.
 */
public class User {

	public String firstName;
	public String lastName;
	public String email;
	public List<String> installedApps = new ArrayList<String>();
	public String provider;
	public String providerId;
	public List<String> friendIds = new ArrayList<String>();
}