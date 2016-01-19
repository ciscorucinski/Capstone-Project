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
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements
                                                     GoogleApiClient.OnConnectionFailedListener,
                                                     View.OnClickListener {

	public static final String TAG = LoginActivity.class.getName();

	private GoogleApiClient mGoogleApiClient;
	private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.authenticate_activity);

	    context = this;
	    boolean is_user_signed_in = Authenticator.isUserSignedIn(this);

	    // [START configure_signin]
	    // Configure sign-in to request offline access to the user's ID, basic
	    // profile, and Google Drive. The first time you request a code you will
	    // be able to exchange it for an access token and refresh token, which
	    // you should store. In subsequent calls, the code will only result in
	    // an access token. By asking for profile access (through
	    // DEFAULT_SIGN_IN) you will also get an ID Token as a result of the
	    // code exchange.
	    String serverClientId = getString(R.string.server_client_id);
	    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
			    GoogleSignInOptions.DEFAULT_SIGN_IN)
			    .requestScopes(new Scope("https://www.googleapis.com/auth/youtube"))
			    .requestServerAuthCode(serverClientId)
			    .requestEmail()
			    .build();
	    // [END configure_signin]

	    // Build GoogleAPIClient with the Google Sign-In API and the above options.
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
			    .enableAutoManage(this /* FragmentActivity */,
			                      this /* OnConnectionFailedListener */)
			    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
			    .build();

	    if (is_user_signed_in) {
		    Log.w(TAG, "User IS logged in!");

		    OptionalPendingResult<GoogleSignInResult> pendingResult =
				    Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
		    if (pendingResult.isDone()) {
			    // There's immediate result available.
			    Log.d(TAG, "Auto Sign-in : passed - " + pendingResult.get().isSuccess());
			    updateUI(pendingResult.get().isSuccess());
		    }

        } else {
		    Log.w(TAG, "User IS NOT logged in!");
	    }

	    // Button click listeners
	    SignInButton googleSignInButton = (SignInButton) findViewById(R.id.sign_in_button);

	    googleSignInButton.setStyle(SignInButton.SIZE_WIDE, SignInButton.COLOR_AUTO,
	                                gso.getScopeArray());

	    googleSignInButton.setOnClickListener(this);
	    findViewById(R.id.sign_out_button).setOnClickListener(this);
	    findViewById(R.id.disconnect_button).setOnClickListener(this);

    }

	private void updateUI(boolean signedIn) {

		if (signedIn) {

			((TextView) findViewById(R.id.status)).setText(R.string.signed_in);

			findViewById(R.id.sign_in_button).setVisibility(View.GONE);
			findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);

        } else {

			((TextView) findViewById(R.id.status)).setText(R.string.signed_out);

			findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
			findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);

		}
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == Authenticator.REQUEST_CODE) {
		    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
		    Log.d(TAG, "onActivityResult:GET_AUTH_CODE:success:" + result.getStatus().isSuccess());

		    if (result.isSuccess()) {
			    // [START get_auth_code]

			    GoogleSignInAccount acct = result.getSignInAccount();
			    Authenticator.saveUser(this, acct);

			    // Show signed-in UI.
			    updateUI(true);

			    // TODO(user): send code to server and exchange for access/refresh/ID tokens.
			    // [END get_auth_code]
		    } else {
			    // Show signed-out UI.
			    updateUI(false);
		    }
        }
    }

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// An unresolvable error has occurred and Google APIs (including Sign-In) will not
		// be available.
		Log.d(TAG, "onConnectionFailed:" + connectionResult);
	}

    @Override
    public void onClick(View v) {

	    switch (v.getId()) {

		    case R.id.sign_in_button:
			    getAuthCode();
			    break;
		    case R.id.sign_out_button:
			    signOut();
			    break;
		    case R.id.disconnect_button:
			    revokeAccess();
			    break;

	    }
    }

	private void getAuthCode() {
		// Start the retrieval process for a server auth code.  If requested, ask for a refresh
		// token.  Otherwise, only get an access token if a refresh token has been previously
		// retrieved.  Getting a new access token for an existing grant does not require
		// user consent.
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, Authenticator.REQUEST_CODE);
	}

	private void signOut() {

		Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
				new ResultCallback<Status>() {

					@Override
					public void onResult(Status status) {

						Log.d(TAG, "signOut:onResult:" + status);
						Authenticator.clearCurrentUser(context);
						updateUI(false);
					}
				}                                                       );
	}

	private void revokeAccess() {

		Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
				new ResultCallback<Status>() {

					@Override
					public void onResult(Status status) {

						Log.d(TAG, "revokeAccess:onResult:" + status);
						Authenticator.clearCurrentUser(context);
						updateUI(false);
					}
				}                                                            );
	}
}


