package com.example.rodri.portugalproject;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.rodri.portugalproject.adapter.DrawerItemAdapter;
import com.example.rodri.portugalproject.fragment.HomeFragment;
import com.example.rodri.portugalproject.model.DrawerItem;

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
        /**Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        initialize();

        createDrawerItems();

        if (savedInstanceState == null) {
            displayView(0);
        }

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

        menuIcons.recycle();
    }

    private void displayView(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                // current balance
                break;
            case 2:
                // expenses
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            // some methods related to the navigation drawer will be implemented here
        } else {
            Log.e("MainActivity", "Error while trying to create fragment.");
        }

    }



}
