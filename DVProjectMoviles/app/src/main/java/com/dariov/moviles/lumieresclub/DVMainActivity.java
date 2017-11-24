package com.dariov.moviles.lumieresclub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.adapters.DVPagerAdapterFragments;
import com.dariov.moviles.lumieresclub.fragments.DVFragmentListado;
import com.dariov.moviles.lumieresclub.utilities.DVLoginSingleton;
import com.dariov.moviles.lumieresclub.interfaces.DVListenerActualizarFoto;
import com.dariov.moviles.lumieresclub.models.DVItemMenu;
import com.dariov.moviles.lumieresclub.models.DVUsuario;
import com.dariov.moviles.lumieresclub.utilities.DVHiloDescarga;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;

public class DVMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener,
        FacebookCallback<LoginResult>, View.OnClickListener, DVListenerActualizarFoto {
    private TextView _txtNomUser, _btnLoginFace;
    private DVPagerAdapterFragments _pagerAdapterFragments;
    private LinkedList<DVFragmentListado> _listaFrags;
    private TwitterLoginButton _twitterLoginButton;
    private AccessTokenTracker _accessTokenTracker;
    private TextView _txtSignout, _txtSignIn;
    private CallbackManager callbackManager;
    private TabLayout _tabLayoutMain;
    private DVUsuario _usuarioLog;
    private ViewPager _viewPager;
    private ImageView _imgPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            _usuarioLog = getIntent().getExtras().getParcelable("dvusuario");
        }
        DVLoginSingleton._listenerActualizarFoto = this;
        setContentView(R.layout.activity_dvmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionBottom);
        floatingActionButton.setOnClickListener(this);
        _tabLayoutMain = (TabLayout) findViewById(R.id.tabLayoutMain);
        _viewPager = (ViewPager) findViewById(R.id.viewPagerMain);

        View loginHeader = navigationView.getHeaderView(0);
        _imgPerfil = loginHeader.findViewById(R.id.imgPerfil);
        _txtSignIn = loginHeader.findViewById(R.id.txtSignIn);
        _txtNomUser = loginHeader.findViewById(R.id.txtNomUser);
        _txtSignout = loginHeader.findViewById(R.id.txtSignOut);
        _btnLoginFace = loginHeader.findViewById(R.id.btnLoginFace);
        _twitterLoginButton = loginHeader.findViewById(R.id.btnLoginTwitter);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
        _btnLoginFace.setOnClickListener(this);
        _txtSignout.setOnClickListener(this);
        _imgPerfil.setOnClickListener(this);

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
            _twitterLoginButton.setVisibility(View.GONE);
        } else {
            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
            TwitterAuthToken authToken = session.getAuthToken();
            String token = authToken.token;
            String secret = authToken.secret;
            _txtNomUser.setText(session.getUserName());
            if (token != null && secret != null) {
                _twitterLoginButton.setVisibility(View.VISIBLE);
                _txtSignIn.setVisibility(View.GONE);
                _btnLoginFace.setVisibility(View.GONE);
                _txtSignout.setVisibility(View.VISIBLE);
            } else {
                _btnLoginFace.setTag(false);
                _btnLoginFace.setText(getResources().getString(R.string.txt_facebook_login));
                setDatosLoginFacebook("", "", R.drawable.profile_pic_placeholder);
                _txtSignout.setVisibility(View.GONE);
                _btnLoginFace.setVisibility(View.VISIBLE);
            }
        }

        _twitterLoginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e(this.getClass().getSimpleName(), "-------Twitter-succes---> " + result);
                _txtSignIn.setVisibility(View.GONE);
                _txtSignout.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e(this.getClass().getSimpleName(), "-------Twitter-failure---> " + exception);
            }
        });
        _tabLayoutMain.addOnTabSelectedListener(this);
        _listaFrags = new LinkedList<>();
        _pagerAdapterFragments = new DVPagerAdapterFragments(getSupportFragmentManager());
        DVHiloDescarga hiloDescarga = new DVHiloDescarga(new DVHiloDescarga.DVListenerHiloDescarga() {
            @Override
            public void onHiloDescargaSuccess(String res) {
                try {
                    JSONArray jsonArray = new JSONArray(res);
                    if (jsonArray.length() > 0) {
                        for (int i=0; i < jsonArray.length(); i++) {
                            DVItemMenu itemMenu = new DVItemMenu(jsonArray.optJSONObject(i));
                            _tabLayoutMain.addTab(_tabLayoutMain.newTab().setTag(itemMenu));
                            _listaFrags.add(DVFragmentListado.newInstance(itemMenu));
                            if (i == jsonArray.length()-1) {
                                _pagerAdapterFragments.setListAdapter(_listaFrags);
                                _viewPager.setAdapter(_pagerAdapterFragments);
                                _viewPager.setOffscreenPageLimit(jsonArray.length());
                                _tabLayoutMain.setupWithViewPager(_viewPager);
                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.e("ActivityMain", "-------JSON-Exception------> " + e);
                    e.printStackTrace();
                }
            }
        });
        hiloDescarga.execute(URI.create("http://www.elevation.com.mx/pages/pruebas/moviles/DVMenu.php"));

        if (_usuarioLog != null) {
            DVLoginSingleton.setDatos(
                    _usuarioLog.get_nombre(),
                    _usuarioLog.get_apellidoP(),
                    _usuarioLog.get_apellidoM(),
                    _usuarioLog.get_correo(),
                    _usuarioLog.get_contrasenia(),
                    _usuarioLog.get_urlImg());
            _txtNomUser.setText(getResources().getString(R.string.txt_mensaje_bienvenido) + "\n " + _usuarioLog.get_nombre() + " " + _usuarioLog.get_apellidoP());
            _txtSignIn.setVisibility(View.GONE);
            _txtSignout.setVisibility(View.VISIBLE);
            if (_usuarioLog.get_urlImg() != null && !_usuarioLog.get_urlImg().equals("")) {
                Picasso.with(_imgPerfil.getContext()).
                        load(_usuarioLog.get_urlImg()).
                        into(_imgPerfil, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.e("DVActivityMain", " ----------onSuccess------> ");
                            }

                            @Override
                            public void onError() {
                                Log.e("DVActivityMain", " ----------OnError------> ");
                            }
                        });
            }
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
        _twitterLoginButton.onActivityResult(requestCode, resultCode, data);
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
        if (id == R.id.itemConfig) {
            Intent intent = new Intent(DVMainActivity.this, DVActivityConfig.class);
            startActivity(intent);
        } else if (id == R.id.itemNuevoArticulo) {
            Intent intent = new Intent(DVMainActivity.this, DVActivityNuevoArticulo.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        DVFragmentListado fragmentListado = null;
        if (tab.getText() != null && !tab.getText().equals("")) {
            switch (tab.getText().toString()) {
                case "Portada":
                    fragmentListado = (DVFragmentListado) _pagerAdapterFragments.getItem(tab.getPosition());
                    break;
                case "Sugerencias Semanal":
                    fragmentListado = (DVFragmentListado) _pagerAdapterFragments.getItem(tab.getPosition());
                    break;
                case "Eventos":
                    fragmentListado = (DVFragmentListado) _pagerAdapterFragments.getItem(tab.getPosition());
                    break;
                default:
                    break;
            }
        }
        if (fragmentListado != null) {
            fragmentListado.DescargaInformacion(fragmentListado.url);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.e("ActivityMain", " ------tab-reselected-----> " + tab.getText());
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e(this.getClass().getSimpleName(), " --------onSuccessFacebook------> " + loginResult);
        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
            setDatosLoginFacebook(Profile.getCurrentProfile().getFirstName(),
                    Profile.getCurrentProfile().getMiddleName() + " " +Profile.getCurrentProfile().getMiddleName(),
                    Profile.getCurrentProfile().getProfilePictureUri(100, 100));
            _twitterLoginButton.setVisibility(View.GONE);
            _txtSignIn.setVisibility(View.GONE);
            _txtSignout.setVisibility(View.VISIBLE);
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
                _twitterLoginButton.setVisibility(View.VISIBLE);
            } else {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                _accessTokenTracker.startTracking();
                _btnLoginFace.setText(getResources().getString(R.string.txt_facebook_logout));
                _btnLoginFace.setTag(true);
                _twitterLoginButton.setVisibility(View.GONE);
            }
        } else if (view.getId() == R.id.floatActionBottom) {
            Intent intent = new Intent(DVMainActivity.this, DVActivityNuevoArticulo.class);
            startActivity(intent);
        } else if (view.getId() == R.id.imgPerfil){
            Intent intent = new Intent(DVMainActivity.this, DVActivityPerfil.class);
            startActivity(intent);
        } else if (view.getId() == R.id.txtSignOut) {
            _txtSignIn.setVisibility(View.GONE);
            _txtSignout.setVisibility(View.VISIBLE);
            DVLoginSingleton.setDatos("", "","","", "", "");
            _txtNomUser.setText("");
            LoginManager.getInstance().logOut();
            Picasso.with(_imgPerfil.getContext()).
                    load(R.drawable.profile_pic_placeholder).
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

    private void setDatosLoginFacebook(String nombre, String apellidos, Uri urlImg) {
        _txtNomUser.setText(nombre);
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

    @Override
    public void actualizarFoto() {
        Log.e("DVActivityMain", " ---------Llegoaqui-actualizarfoto-------->");
        _imgPerfil.setImageBitmap(DVLoginSingleton._bitmapImg);
    }
}
