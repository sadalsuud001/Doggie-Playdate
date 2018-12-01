package com.example.xin.pre_project;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.xin.pre_project.Fragments.AccountSettingsFragment;
import com.example.xin.pre_project.Fragments.AddDogFragment;
import com.example.xin.pre_project.Fragments.CreatePlaydateFragment;
import com.example.xin.pre_project.Fragments.MessagingFragment;
import com.example.xin.pre_project.Fragments.MyPlaydatesFragment;
import com.example.xin.pre_project.Fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity

        implements
        MyPlaydatesFragment.PlaydateListener,
        CreatePlaydateFragment.AfterCreatePlaydate {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private Bundle b;
    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_MESSAGING = "messaging";
    private static final String TAG_CREATEPLAYDATE = "createplaydate";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_MYPLAYDATES = "myplaydates";
    private static final String TAG_ACCOUNTSETTINGS = "accountsettings";
    private static final String TAG_ADDDOG = "adddog";

    private Fragment fragment;
    public static String CURRENT_TAG = TAG_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav_drawer);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setUpNavigationView();

        b = getIntent().getExtras();
        getIntent().removeExtra("navItemIndex");

        if(savedInstanceState != null) {
            navItemIndex = savedInstanceState.getInt("saveIndex", 0);
            switch(navItemIndex) {
                case 1:
                    CURRENT_TAG = TAG_MESSAGING;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new MessagingFragment();
                    navToFragment(fragment);
                    break;
                case 2:
                    CURRENT_TAG = TAG_CREATEPLAYDATE;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new CreatePlaydateFragment();
                    navToFragment(fragment);
                    break;
                case 3:
                    CURRENT_TAG = TAG_MYPLAYDATES;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new MyPlaydatesFragment();
                    navToFragment(fragment);
                    break;
                case 4:
                    CURRENT_TAG = TAG_PROFILE;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new ProfileFragment();
                    navToFragment(fragment);
                    break;
                case 5:
                    CURRENT_TAG = TAG_ACCOUNTSETTINGS;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new AccountSettingsFragment();
                    navToFragment(fragment);
                    break;
                case 6:
                    CURRENT_TAG = TAG_ADDDOG;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new AddDogFragment();
                    navToFragment(fragment);
                    break;
                default:
                    CURRENT_TAG = TAG_MYPLAYDATES;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new MyPlaydatesFragment();
                    navToFragment(fragment);
            }

        }
        else if(b != null) {
            navItemIndex = b.getInt("navItemIndex", 1);
            switch (navItemIndex) {
                case 1:
                    CURRENT_TAG = TAG_MESSAGING;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new MessagingFragment();
                    navToFragment(fragment);
                    break;
                case 2:
                    CURRENT_TAG = TAG_CREATEPLAYDATE;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new CreatePlaydateFragment();
                    navToFragment(fragment);
                    break;
                case 3:
                    CURRENT_TAG = TAG_MYPLAYDATES;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new MyPlaydatesFragment();
                    navToFragment(fragment);
                    break;
                case 4:
                    CURRENT_TAG = TAG_PROFILE;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new ProfileFragment();
                    navToFragment(fragment);
                    break;
                case 5:
                    CURRENT_TAG = TAG_ACCOUNTSETTINGS;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new AccountSettingsFragment();
                    navToFragment(fragment);
                    break;
                case 6:
                    CURRENT_TAG = TAG_ADDDOG;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new AddDogFragment();
                    navToFragment(fragment);
                    break;
                default:
                    CURRENT_TAG = TAG_MYPLAYDATES;
                    selectNavMenu(navItemIndex);
                    fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    if (fragment == null)
                        fragment = new MyPlaydatesFragment();
                    navToFragment(fragment);
            }
        }
        else {
            // error in navigating, go back to home screen
            finish();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("saveIndex", navItemIndex);
        //outState.putString("CURRENT_TAG", CURRENT_TAG);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navI_home:
                                navItemIndex = 0;
                                selectNavMenu(navItemIndex);
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("navItemIndex", navItemIndex);
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                                break;
                            case R.id.navI_messaging:
                                navItemIndex = 1;
                                CURRENT_TAG = TAG_MESSAGING;
                                selectNavMenu(navItemIndex);
                                fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                                if(fragment == null)
                                    fragment = new MessagingFragment();
                                navToFragment(fragment);
                                break;
                            case R.id.navI_makeplaydate:
                                navItemIndex = 2;
                                CURRENT_TAG = TAG_CREATEPLAYDATE;
                                selectNavMenu(navItemIndex);
                                fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                                if(fragment == null)
                                    fragment = new CreatePlaydateFragment();
                                navToFragment(fragment);
                                break;
                            case R.id.navI_playdates:
                                navItemIndex = 3;
                                CURRENT_TAG = TAG_MYPLAYDATES;
                                selectNavMenu(navItemIndex);
                                fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                                if(fragment == null)
                                    fragment = new MyPlaydatesFragment();
                                navToFragment(fragment);
                                break;
                            case R.id.navI_userprofile:
                                navItemIndex = 4;
                                CURRENT_TAG = TAG_PROFILE;
                                selectNavMenu(navItemIndex);
                                fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                                if(fragment == null)
                                    fragment = new ProfileFragment();
                                navToFragment(fragment);
                                break;
                            case R.id.navI_settings:
                                navItemIndex = 5;
                                CURRENT_TAG = TAG_ACCOUNTSETTINGS;
                                selectNavMenu(navItemIndex);
                                fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                                if(fragment == null)
                                    fragment = new AccountSettingsFragment();
                                navToFragment(fragment);
                                break;
                            case R.id.action_logout:
                                /*
                                    TODO: Log user out. Any DB calls? Any write to disk?
                                */
                                // GO TO LOGIN SCREEN
                                Toast.makeText(getApplicationContext(), "Log out user", Toast.LENGTH_SHORT).show();
                                navItemIndex = 6;
                                CURRENT_TAG = TAG_ADDDOG;
                                selectNavMenu(navItemIndex);
                                fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                                if(fragment == null)
                                    fragment = new AddDogFragment();
                                navToFragment(fragment);
                                break;
                            default:
                                navItemIndex = 0;
                        }

                        return true;
                    }
                });

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };

            drawer.setDrawerListener(actionBarDrawerToggle);

            actionBarDrawerToggle.syncState();
        }

    }

    private void selectNavMenu(int navItemIndex) {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        if (drawer != null)
            drawer.closeDrawers();
    }

    public void navToFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    // Implement PlaydatesListener interface
    public void playdatesFabGoToCreatePlaydateFragment() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(false);
        navItemIndex = 2;
        CURRENT_TAG = TAG_CREATEPLAYDATE;
        selectNavMenu(navItemIndex);
        fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        if(fragment == null)
            fragment = new CreatePlaydateFragment();
        navToFragment(fragment);
    }

    // Implement CreatePlaydatesFragment interface
    public void goToMyPlaydates() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(false);
        navItemIndex = 3;
        CURRENT_TAG = TAG_MYPLAYDATES;
        selectNavMenu(navItemIndex);
        fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        if(fragment == null)
            fragment = new MyPlaydatesFragment();
        navToFragment(fragment);
    }
}