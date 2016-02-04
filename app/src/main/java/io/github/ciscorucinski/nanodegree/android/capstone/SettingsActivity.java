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
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On handset devices,
 * settings are presented as a single list. On tablets, settings are split by category, with
 * category headers shown to the left of the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html"> Android Design:
 * Settings</a> for design guidelines and the
 * <a href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {

    static boolean isActivityCreated;
    /**
     * A preference value change listener that updates the preference's summary to reflect its new
     * value.
     */
    private static ContextualOnPreferenceChangeListener bindingListener =
            new ContextualOnPreferenceChangeListener();

    /**
     * Binds a preference's summary to its value. More specifically, when the preference's value is
     * changed, its summary (line of text below the preference title) is updated to reflect the
     * value. The summary is also immediately updated upon calling this method. The exact display
     * format is dependent on the type of preference.
     *
     * @see #bindingListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(bindingListener);

        Object preferenceObject;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                preference.getContext());

        if (preference instanceof MultiSelectListPreference) {

            preferenceObject = defaultSharedPreferences.getStringSet(preference.getKey(), new HashSet<String>());

        } else if (preference instanceof SwitchPreference) {

            preferenceObject = defaultSharedPreferences.getBoolean(preference.getKey(), false);

        } else {

            preferenceObject = defaultSharedPreferences.getString(preference.getKey(), "");
        }

        // Trigger the listener immediately with the preference's
        // current value.
        bindingListener.onPreferenceChange(preference, preferenceObject);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        bindingListener.addContext(this);

        setContentView(R.layout.settings);

        setupActionBar();

        //		getFragmentManager().beginTransaction()
        //				.replace(R.id.settings_content, new SettingsFragment())
        //				.addToBackStack(null)
        //				.commit();

        isActivityCreated = true;

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private static class ContextualOnPreferenceChangeListener
            implements Preference.OnPreferenceChangeListener {

        private Context context;

        public ContextualOnPreferenceChangeListener() {
        }

        public void addContext(Context context) {
            this.context = context;
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            String stringValue = value.toString();

            if (preference instanceof MultiSelectListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                MultiSelectListPreference listPreference =
                        (MultiSelectListPreference) preference;

                HashSet selectedItems = (HashSet) value;
                CharSequence[] allItems = listPreference.getEntries();

                // Set the summary to reflect the new value.
                preference.setSummary(
                        String.format("%s of %s categories displayed",
                                selectedItems.size(),
                                allItems.length));

            } else if (preference instanceof SwitchPreference) {

                if (isActivityCreated) Authenticator.clearCurrentUser(context);

                preference.setSummary(Authenticator.getCurrentUser().getName());

                if (isActivityCreated) ((AppCompatActivity) context).finish();

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }

    }


    //	@Override
    //	public boolean onMenuItemSelected(int featureId, MenuItem item) {
    //
    //		int id = item.getItemId();
    //		if (id == android.R.id.home) {
    //			if (!super.onMenuItemSelected(featureId, item)) {
    //				NavUtils.navigateUpFromSameTask(this);
    //			}
    //			return true;
    //		}
    //		return super.onMenuItemSelected(featureId, item);
    //	}

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            bindPreferenceSummaryToValue(
                    findPreference(getString(R.string.pref_category_display_key)));
            bindPreferenceSummaryToValue(
                    findPreference(getString(R.string.pref_account_logout_key)));

        }


    }
}
