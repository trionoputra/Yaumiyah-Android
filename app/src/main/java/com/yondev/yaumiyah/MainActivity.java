package com.yondev.yaumiyah;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yondev.yaumiyah.fragment.RecapFragment;
import com.yondev.yaumiyah.fragment.SettingFragment;
import com.yondev.yaumiyah.fragment.TodayFragment;
import com.yondev.yaumiyah.sqlite.DatabaseHelper;
import com.yondev.yaumiyah.sqlite.DatabaseManager;
import com.yondev.yaumiyah.utils.Shared;

import net.danlew.android.joda.JodaTimeAndroid;

import java.lang.reflect.Field;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView txtHadist;
    private ActionBarDrawerToggle toggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    public final static int ADD_TARGET = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Shared.initialize(getBaseContext());
        JodaTimeAndroid.init(this);

        DatabaseManager.initializeInstance(new DatabaseHelper(MainActivity.this));



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolBarTextView(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_today);

        View headerView = navigationView.getHeaderView(0);
        if(headerView != null)
        {
            txtHadist = (TextView) headerView.findViewById(R.id.txtHadist);
            if(txtHadist != null)
                txtHadist.setTypeface(Shared.appfontLight);
        }

        initScreen();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent  intent = new Intent(MainActivity.this,AddTargetActivity.class);
            startActivityForResult(intent,ADD_TARGET);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String tag = "";
        Fragment fragment = null;

        toogleMenu(false);
        if (id == R.id.nav_today) {
            fragment = new TodayFragment();
            tag = "TodayFragment";
            toogleMenu(true);
        } else if (id == R.id.nav_recap) {
            fragment = new RecapFragment();
            tag = "RecapFragment";
        } else if (id == R.id.nav_setting) {
            fragment  = new SettingFragment();
            tag = "SettingFragment";
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment,tag).commit();

        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void initScreen()
    {
        setTitle(R.string.daftar_rencana);
        Fragment fragment = new TodayFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment,"TodayFragment").commit();
    }

    private void setToolBarTextView(Toolbar toolbar) {
        TextView titleTextView = null;

        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            titleTextView.setTypeface(Shared.appfontBold);

        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
    }

    private void toogleMenu(boolean show)
    {
        this.menu.findItem(R.id.action_add).setVisible(show);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("TodayFragment");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
