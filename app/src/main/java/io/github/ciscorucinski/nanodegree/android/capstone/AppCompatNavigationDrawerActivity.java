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

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public abstract class AppCompatNavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    protected void setupToolbarAndNavigation() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        NavigationView navigationSettingsView = (NavigationView) findViewById(R.id.nav_setting);

        navigationSettingsView.setScrollY(100);    // This is needed to move up the bottom Settings & Help Navigation View. The current implementation of NavigationView does not allow for my layout easily.
        navigationView.setNavigationItemSelectedListener(this);
        navigationSettingsView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.action_settings:

                startActivity(new Intent(this, SettingsActivity.class));
                break;

            //        if (id == R.id.nav_camera) {
            //            // Handle the camera action
            //        } else if (id == R.id.nav_gallery) {
            //
            //        } else if (id == R.id.nav_slideshow) {
            //
            //        } else if (id == R.id.nav_manage) {
            //
            //        } else if (id == R.id.nav_share) {
            //
            //        } else if (id == R.id.nav_send) {
            //
            //        }

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

}
