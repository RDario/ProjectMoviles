package com.dariov.moviles.lumieresclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by DarioValdes on 04/10/2017.
 */

public class DVActivityLogin extends AppCompatActivity implements FacebookCallback<LoginResult>, View.OnClickListener {
    private TextView _btnLoginFace, _btnContinuar, _btnIniciarSesion;
    private TwitterLoginButton _twitterLoginButton;
    private AccessTokenTracker _accessTokenTracker;
    private ExpandableLayout _expandableView;
    private CallbackManager callbackManager;
    private EditText _editCorreo, _editPass;
    private Button _btnSignin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        SharedPreferences sharedPref = getSharedPreferences("preferenceConfig", Context.MODE_PRIVATE);
        String prefeIdioma = sharedPref.getString("prefeIdioma", "");
        if (prefeIdioma.equals("Spanish")) {
            setLocale("es");
        } else {
            setLocale("en");
        }
        setContentView(R.layout.dv_activity_login);
        _twitterLoginButton = (TwitterLoginButton) findViewById(R.id.btnLoginTwitter);
        _btnContinuar = (TextView) findViewById(R.id.btnContinuar);
        _btnLoginFace = (TextView) findViewById(R.id.btnLoginFace);
        _btnIniciarSesion = (TextView) findViewById(R.id.btnIniciarSesion);
        _expandableView = (ExpandableLayout) findViewById(R.id.expandableView);
        _btnSignin = (Button) findViewById(R.id.btnSignIn);
        _editCorreo = (EditText) findViewById(R.id.editCorreo);
        _editPass = (EditText) findViewById(R.id.editContrasenia);
        _btnSignin.setOnClickListener(this);
        _btnLoginFace.setOnClickListener(this);
        _btnContinuar.setOnClickListener(this);
        _btnIniciarSesion.setOnClickListener(this);
        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
            Intent intent = new Intent(DVActivityLogin.this, DVMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager, this);
            _accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    Log.e(this.getClass().getSimpleName(), " --------currentAccessToken------> " + oldAccessToken + " " + currentAccessToken);
                }
            };
        }

        _twitterLoginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e("Twitter", " --------twittersession-success-----> " + result);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Twitter", " --------twittersession-failure-----> " + exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        _twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLoginFace) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            _accessTokenTracker.startTracking();
        } else if (view.getId() == R.id.btnLoginTwitter) {

        } else if (view.getId() == R.id.btnContinuar) {
            Intent intent = new Intent(DVActivityLogin.this, DVMainActivity.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.btnIniciarSesion) {
            if (_expandableView.isExpanded()) {
                _expandableView.collapse();
            } else {
                _expandableView.expand();
                _expandableView.requestFocus();
            }
        } else if (view.getId() == R.id.btnSignIn) {
            String correo = _editCorreo.getText().toString();
            String password = _editPass.getText().toString();
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("txtCorreo", correo);
            hashmap.put("txtPassword", password);
            DVHiloDescarga hiloDescarga = new DVHiloDescarga(new DVHiloDescarga.DVListenerHiloDescarga() {
                @Override
                public void onHiloDescargaSuccess(String res) {
                    Log.e("DVActivityLogin", " ----------res------> " + res);
                    try {
                        DVUsuario usuario = null;
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() > 0) {
                            for (int i=0; i < jsonArray.length(); i++) {
                                usuario = new DVUsuario(jsonArray.optJSONObject(i));
                            }
                        }
                        Intent intent = new Intent(DVActivityLogin.this, DVMainActivity.class);
                        intent.putExtra("dvusuario", usuario);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        Log.e("DVActivityLogin", " ----------JSON-Exception------> " + e1);
                    }
                }
            }, hashmap);
            hiloDescarga.execute(URI.create("http://www.elevation.com.mx/pages/pruebas/moviles/iniciarsesion.php"));
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Intent intent = new Intent(DVActivityLogin.this, DVMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException error) {
    }

    public void setLocale(String lang) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(lang));
        }
        res.updateConfiguration(conf, dm);
    }
}
