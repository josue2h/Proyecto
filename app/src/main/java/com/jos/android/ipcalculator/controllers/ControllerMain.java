package com.jos.android.ipcalculator.controllers;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import com.jos.android.ipcalculator.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ControllerMain extends AppCompatActivity{

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_container)
    RelativeLayout main_container;
    @Bind(R.id.navigation_view)
    NavigationView navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controller_main);
        ButterKnife.bind(this);

        prepareToolbar();
        prepareNavigationView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer_layout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareToolbar() {
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepareNavigationView() {
        if (navigation_view != null) {
            prepareDrawer();
            selectedItem(navigation_view.getMenu().getItem(0));
        }
    }

    private void prepareDrawer() {
        navigation_view.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        selectedItem(menuItem);
                        drawer_layout.closeDrawers();
                        return true;
                    }
                });
    }

    private void selectedItem(MenuItem item) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.item_home:
                fragment = new ControllerHome();
                break;
            case R.id.item_classless:
                fragment = new ControllerClassless();
                break;
            case R.id.item_classful:
                fragment = new ControllerClassful();
                break;
            case R.id.item_subred_homo:
                fragment = new ControllerSubnet();
                break;
            case R.id.item_subred_hete:
                fragment = new ControllerSubnetHete();
                break;
            case R.id.item_superred:
                fragment = new ControllerSupernet();
                break;
        }
        fragment.setRetainInstance(true);
        setTitle(item.getTitle());
        toolbar.setLogo(item.getIcon());
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
        }
    }
}
