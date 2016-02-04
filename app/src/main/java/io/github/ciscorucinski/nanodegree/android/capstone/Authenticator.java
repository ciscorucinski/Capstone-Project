/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Map;

public class Authenticator {

	public static final int REQUEST_CODE = 9003;
	public static final String USER_LOGGED_OUT = "invalid";

	private static User currentUser = User.getNullUser();

	public static void saveUser(Context context, GoogleSignInAccount acct) {

		SharedPreferences.Editor authEditor = Authenticator.getPreferences(context).edit();

		authEditor.putBoolean(Authenticator.Key.USER_SIGNED_IN, true);

		authEditor.putString(Authenticator.Key.AUTH_CODE, acct.getServerAuthCode());
		authEditor.putString(Authenticator.Key.DISPLAY_NAME, acct.getDisplayName());
		authEditor.putString(Authenticator.Key.EMAIL, acct.getEmail());
		authEditor.putString(Authenticator.Key.ID, acct.getId());
		authEditor.putString(Authenticator.Key.ID_TOKEN, acct.getIdToken());
		authEditor.putString(Authenticator.Key.PHOTO_URL, acct.getPhotoUrl().toString());

		authEditor.apply();

		currentUser = new User();
		currentUser.setAuthCode(acct.getServerAuthCode());
		currentUser.setName(acct.getDisplayName());
		currentUser.setEmail(acct.getEmail());
		currentUser.setID(acct.getId());
		currentUser.setToken(acct.getIdToken());
		currentUser.setPhotoURL(acct.getPhotoUrl().toString());

	}

	private static SharedPreferences getPreferences(Context context) {

		return context.getSharedPreferences(File.PREF, File.MODE);

	}

	public static String getUserAuthenticationCode(Context context) {

		if (isUserSignedIn(context)) {

			return getPreferences(context).getString(Key.ID_TOKEN, Authenticator.USER_LOGGED_OUT);

		} else {

			return Authenticator.USER_LOGGED_OUT;

		}


	}

	public static boolean isUserSignedIn(Context context) {

		return getPreferences(context).getBoolean(Key.USER_SIGNED_IN, false);

	}

	public static void clearCurrentUser(Context context) {

		if (!isUserSignedIn(context)) return;

		getPreferences(context).edit().clear().apply();
		currentUser = User.getNullUser();

	}

	public static void log(Context context) {

		// Define default return values. These should not display, but are needed
		final String STRING_ERROR = "error!";
		final Boolean BOOL_ERROR = false;

		// Loop through the Shared Prefs
		Log.i("Loading Shared Prefs", "-----------------------------------");
		Log.i("------------------", "-------------------------------------");

		SharedPreferences preference = getPreferences(context);
		Map<String, ?> prefMap = preference.getAll();

		Object prefObj;
		Object prefValue = null;

		for (String key : prefMap.keySet()) {

			prefObj = prefMap.get(key);

			if (prefObj instanceof String) prefValue = preference.getString(key, STRING_ERROR);
			if (prefObj instanceof Boolean) prefValue = preference.getBoolean(key, BOOL_ERROR);

			Log.i(String.format("Shared Preference : %s - %s", File.PREF, key),
					String.valueOf(prefValue));

		}

		Log.i("------------------", "-------------------------------------");
		Log.i("Loaded Shared Prefs", "------------------------------------");

	}

	public static User getCurrentUser() {

		return currentUser;

	}

	public static class Key {

		public static final String USER_SIGNED_IN = "user_signed_in";
		public static final String DISPLAY_NAME = "display_name";
		public static final String EMAIL = "email";
		public static final String ID = "id";
		public static final String ID_TOKEN = "id_token";
		public static final String AUTH_CODE = "server_auth_code";
		public static final String PHOTO_URL = "photo_url";

	}

	private static class File {

		private static final String PREF = "Authentication";
		private static final int MODE = Context.MODE_PRIVATE;

	}

	public static class User {

		private String name;
		private String email;
		private String ID;
		private String token;
		private String photoURL;
		private String authCode;

		public static User getNullUser() {

			User nullUser = new User();
			nullUser.setName("No user logged in!");
			nullUser.setEmail("");
			nullUser.setPhotoURL("");
			nullUser.setToken("");
			nullUser.setID("");
			nullUser.setAuthCode("");

			return nullUser;

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getID() {
			return ID;
		}

		public void setID(String ID) {
			this.ID = ID;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getPhotoURL() {
			return photoURL;
		}

		public void setPhotoURL(String photoURL) {
			this.photoURL = photoURL;
		}

		public String getAuthCode() {
			return authCode;
		}

		public void setAuthCode(String authCode) {
			this.authCode = authCode;
		}
	}

}