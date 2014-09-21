package com.codepath.gridimagesearch.models;

import java.io.Serializable;

public class SearchOptions implements Serializable {
	private static final long serialVersionUID = -16710054704871464L;
	
	public static String BLANK = "";
	public String imageSize;
	public String colorFilter;
	public String imageType;
	public String siteFilterUrl;
	
	public SearchOptions() {
		imageSize = BLANK;
		colorFilter = BLANK;
		imageType = BLANK;
		siteFilterUrl = BLANK;
	}
}
