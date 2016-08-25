package com.netty.server.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceUtil {
	private final static ResourceControl control = new ResourceControl();

	public static long getActiveTime(){
		return ResourceControl.REFRESH_TIME;
	}
	
	public static String load(String fileName, String resource) {
		return getBundle(fileName).getString(resource);
	}

	public static ResourceBundle getBundle(String fileName) {
		return ResourceBundle.getBundle(fileName, Locale.getDefault(), control);
	}

	private static class ResourceControl extends ResourceBundle.Control { 

		private static long REFRESH_TIME = 60 * 1000;
				
		@Override
		public long getTimeToLive(String baseName, Locale locale) {
			return REFRESH_TIME;
		}

		@Override
		public boolean needsReload(String baseName, Locale locale,
				String format, ClassLoader loader, ResourceBundle bundle,
				long loadTime) {
			return true;
		}
	}
}
