/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Authenticator {

	public static final int REQUEST_CODE = 9003;
	public static final String USER_LOGGED_OUT = "invalid";

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

		getPreferences(context).edit().clear().apply();

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

}