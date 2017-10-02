package com.dariov.moviles.lumieresclub;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dariov.moviles.lumieresclub.adapters.DVPagerAdapterFragments;
import com.dariov.moviles.lumieresclub.fragments.DVFragmentListado;

import java.util.LinkedList;

public class DVMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {
    private DVPagerAdapterFragments _pagerAdapterFragments;
    private LinkedList<Fragment> _listaFrags;
    private TabLayout _tabLayoutMain;
    private ViewPager _viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _tabLayoutMain = (TabLayout) findViewById(R.id.tabLayoutMain);
        _tabLayoutMain.addOnTabSelectedListener(this);
        _viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        _listaFrags = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = _tabLayoutMain.newTab();
            tab.setText("Tab" + i);
            _tabLayoutMain.addTab(tab);
            _listaFrags.add(DVFragmentListado.newInstance(i, tab.getText() != null ? tab.getText().toString(): "Tab"));
        }
        _pagerAdapterFragments = new DVPagerAdapterFragments(getSupportFragmentManager());
        _pagerAdapterFragments.setListAdapter(_listaFrags);
        _viewPager.setAdapter(_pagerAdapterFragments);
        _tabLayoutMain.setupWithViewPager(_viewPager);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.dvmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e(getClass().getSimpleName(), " ------tab------> " + tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
