package com.example.rodri.letsgetout.activity;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.adapter.DrawerItemAdapter;
import com.example.rodri.letsgetout.fragment.CurrentBalanceFragment;
import com.example.rodri.letsgetout.fragment.ExpensesAndSavingsFragment;
import com.example.rodri.letsgetout.fragment.HomeFragment;
import com.example.rodri.letsgetout.fragment.StatisticsFragment;
import com.example.rodri.letsgetout.model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence drawerTitle;
    private CharSequence title;

    private String[] menuTitles;
    private TypedArray menuIcons;

    private List<DrawerItem> drawerItems;
    private DrawerItemAdapter drawerItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views and other variables
        initialize();
        createDrawerItems();

        drawerItemAdapter = new DrawerItemAdapter(this, 0, drawerItems);
        drawerListView.setAdapter(drawerItemAdapter);

        // Enable Action Bar and making it behave as a Toggle Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // 'Gambiarra' used to change the Navigation Drawer icon
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_drawer, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        Toolbar toolbar = new Toolbar(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                // call onPreparedOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                // call onPreparedOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }

        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            displayView(0);
        }

        drawerListView.setOnItemClickListener(new SlideMenuClickListener());

    }

    private void initialize() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.listSliderMenu);

        drawerTitle = title = getTitle();

        menuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        menuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        drawerItems = new ArrayList<>();
    }

    @SuppressWarnings("ResourceType")
    private void createDrawerItems() {
        drawerItems.add(new DrawerItem(menuTitles[0], menuIcons.getResourceId(0, -1)));
        drawerItems.add(new DrawerItem(menuTitles[1], menuIcons.getResourceId(1, -1)));
        drawerItems.add(new DrawerItem(menuTitles[2], menuIcons.getResourceId(2, -1)));
        drawerItems.add(new DrawerItem(menuTitles[3], menuIcons.getResourceId(3, -1)));

        menuIcons.recycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if the drawer is opened, then hide the action bar items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerListView);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(title);
    }

    /**
     *
     * onPostCreate() and onConfigurationChanged() will be called when using the ActionBarDrawerToggle
     *
     */

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState occurred
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration changes to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Show the proper fragment according to the given position
     *
     * 0 - Home
     * 1 - Current Balance
     * 2 - Expenses And Savings
     * 3 - Statistics
     *
     * @param position
     */
    private void displayView(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CurrentBalanceFragment();
                break;
            case 2:
                fragment = new ExpensesAndSavingsFragment();
                break;
            case 3:
                fragment = new StatisticsFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            // some methods related to the navigation drawer will be implemented here
            drawerListView.setItemChecked(position, true);
            drawerListView.setSelection(position);
            setTitle(menuTitles[position]);
            drawerLayout.closeDrawer(drawerListView);
        } else {
            Log.e("MainActivity", "Error while trying to create fragment.");
        }

    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }



}
