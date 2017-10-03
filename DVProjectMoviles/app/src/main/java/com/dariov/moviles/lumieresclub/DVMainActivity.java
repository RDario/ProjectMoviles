package com.dariov.moviles.lumieresclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.adapters.DVPagerAdapterFragments;
import com.dariov.moviles.lumieresclub.fragments.DVFragmentListado;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.Arrays;
import java.util.LinkedList;

public class DVMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener,
        FacebookCallback<LoginResult>, View.OnClickListener {
    private TextView _txtNomUser, _txtApeUser, _btnLoginFace;
    private AccessTokenTracker _accessTokenTracker;
    private CallbackManager callbackManager;
    private ViewPager _viewPager;
    private ImageView _imgPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_dvmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout _tabLayoutMain = (TabLayout) findViewById(R.id.tabLayoutMain);
        _tabLayoutMain.addOnTabSelectedListener(this);
        _viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        LinkedList<Fragment> _listaFrags = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = _tabLayoutMain.newTab();
            tab.setText("Tab" + i);
            _tabLayoutMain.addTab(tab);
            _listaFrags.add(DVFragmentListado.newInstance(i, tab.getText() != null ? tab.getText().toString(): "Tab"));
        }
        DVPagerAdapterFragments _pagerAdapterFragments = new DVPagerAdapterFragments(getSupportFragmentManager());
        _pagerAdapterFragments.setListAdapter(_listaFrags);
        _viewPager.setAdapter(_pagerAdapterFragments);
        _tabLayoutMain.setupWithViewPager(_viewPager);

        View loginHeader = navigationView.getHeaderView(0);
        _imgPerfil = loginHeader.findViewById(R.id.imgPerfil);
        _txtNomUser = loginHeader.findViewById(R.id.txtNomUser);
        _txtApeUser = loginHeader.findViewById(R.id.txtApeUser);
        _btnLoginFace = loginHeader.findViewById(R.id.btnLoginFace);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
        _btnLoginFace.setOnClickListener(this);

        _accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.e(this.getClass().getSimpleName(), " --------currentAccessToken------> " + oldAccessToken + " " + currentAccessToken);
            }
        };
        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
            _btnLoginFace.setTag(true);
            _btnLoginFace.setText(getResources().getString(R.string.txt_facebook_logout));
            setDatosLoginFacebook(Profile.getCurrentProfile().getFirstName(),
                    Profile.getCurrentProfile().getMiddleName() + " " + Profile.getCurrentProfile().getLastName(),
                    Profile.getCurrentProfile().getProfilePictureUri(100, 100));
        } else {
            _btnLoginFace.setTag(false);
            _btnLoginFace.setText(getResources().getString(R.string.txt_facebook_login));
            setDatosLoginFacebook("", "", R.drawable.profile_pic_placeholder);
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e(this.getClass().getSimpleName(), " --------onSuccessFacebook------> " + loginResult);
        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
            setDatosLoginFacebook(Profile.getCurrentProfile().getFirstName(),
                    Profile.getCurrentProfile().getMiddleName() + " " +Profile.getCurrentProfile().getMiddleName(),
                    Profile.getCurrentProfile().getProfilePictureUri(100, 100));
        }
    }

    @Override
    public void onCancel() {
        Log.e(this.getClass().getSimpleName(), " --------onCancelFacebook------> ");
    }

    @Override
    public void onError(FacebookException error) {
        Log.e(this.getClass().getSimpleName(), " --------onErrorFacebook------> " + error);
        Toast.makeText(getApplicationContext(), "Ocurrió un error. Inténtelo de nuevo más tarde", Toast.LENGTH_LONG).show();
        setDatosLoginFacebook("", "", R.drawable.profile_pic_placeholder);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLoginFace) {
            boolean isLog = (boolean) _btnLoginFace.getTag();
            if (isLog) {
                _accessTokenTracker.stopTracking();
                LoginManager.getInstance().logOut();
                _btnLoginFace.setText(getResources().getString(R.string.txt_facebook_login));
                _btnLoginFace.setTag(false);
                setDatosLoginFacebook("", "", R.drawable.profile_pic_placeholder);
            } else {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                _accessTokenTracker.startTracking();
                _btnLoginFace.setText(getResources().getString(R.string.txt_facebook_logout));
                _btnLoginFace.setTag(true);
            }
        }
    }

    private void setDatosLoginFacebook(String nombre, String apellidos, Uri urlImg) {
        _txtNomUser.setText(nombre);
        _txtApeUser.setText(apellidos);
        Picasso.with(_imgPerfil.getContext()).
                load(urlImg).
                placeholder(R.drawable.profile_pic_placeholder).
                into(_imgPerfil, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Picasso", " ---------onSuccess-----Perfil--->");
                    }

                    @Override
                    public void onError() {
                        Log.e("Picasso", " ---------onError-----Perfil--->");
                    }
                });
    }

    private void setDatosLoginFacebook(String nombre, String apellidos, int intResource) {
        _txtNomUser.setText(nombre);
        _txtApeUser.setText(apellidos);
        Picasso.with(_imgPerfil.getContext()).
                load(intResource).
                placeholder(R.drawable.profile_pic_placeholder).
                into(_imgPerfil, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Picasso", " ---------onSuccess-----Perfil--->");
                    }

                    @Override
                    public void onError() {
                        Log.e("Picasso", " ---------onError-----Perfil--->");
                    }
                });
    }
}
