package com.dariov.moviles.lumieresclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Arrays;

/**
 * Created by DarioValdes on 04/10/2017.
 */

public class DVActivityLogin extends AppCompatActivity implements FacebookCallback<LoginResult>, View.OnClickListener {
    private TwitterLoginButton _twitterLoginButton;
    private AccessTokenTracker _accessTokenTracker;
    private TextView _btnLoginFace, _btnContinuar;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_login);
        _twitterLoginButton = (TwitterLoginButton) findViewById(R.id.btnLoginTwitter);
        _btnContinuar = (TextView) findViewById(R.id.btnContinuar);
        _btnLoginFace = (TextView) findViewById(R.id.btnLoginFace);
        _btnLoginFace.setOnClickListener(this);
        _btnContinuar.setOnClickListener(this);
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
            _twitterLoginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                }

                @Override
                public void failure(TwitterException exception) {
                }
            });
        } else if (view.getId() == R.id.btnContinuar) {
            Intent intent = new Intent(DVActivityLogin.this, DVMainActivity.class);
            startActivity(intent);
            finish();
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
}
