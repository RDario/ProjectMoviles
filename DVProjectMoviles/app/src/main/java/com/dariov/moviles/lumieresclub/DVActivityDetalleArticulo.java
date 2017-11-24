package com.dariov.moviles.lumieresclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.models.DVArticulo;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Creado por el papu Dario Valdes  on 08/10/2017.
 */

public class DVActivityDetalleArticulo extends AppCompatActivity implements View.OnClickListener {
    private TextView _txtTitulo, _txtDescripcion, _txtTexto, _txtAutor, _txtUsuario, _txtCompartir, _txtCompartirTwitter, _txtDescripcionUser;
    private StickyScrollView _stickyScrollView;
    private ExpandableLayout _expandableView;
    private CallbackManager _callbackManager;
    private RelativeLayout _relativeAutor;
    private ShareDialog _shareDialog;
    private ImageView _imgUsuario;
    private DVArticulo _articulo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            _articulo = getIntent().getExtras().getParcelable("dvarticulo");
        }
        setContentView(R.layout.dv_activity_articulo_detalle);
        _txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        _txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        _txtTexto = (TextView) findViewById(R.id.txtTexto);
        _txtAutor = (TextView) findViewById(R.id.txtAutor);
        _txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        _txtDescripcionUser  = (TextView) findViewById(R.id.txtDescripcionUser);
        _txtCompartirTwitter = (TextView) findViewById(R.id.txtCompartirTwitter);
        _txtCompartir  = (TextView) findViewById(R.id.txtCompartir);
        _imgUsuario = (ImageView) findViewById(R.id.imgUsuario);

        _txtCompartir.setText(_txtCompartir.getText() + " Facebook");
        _txtCompartirTwitter.setText(_txtCompartirTwitter.getText() + " Twitter");
        _txtAutor.setText(getResources().getString(R.string.txt_mensaje_written_by) + " " + _txtAutor.getText());
        _stickyScrollView = (StickyScrollView) findViewById(R.id.stickyScrollView);
        _expandableView = (ExpandableLayout) findViewById(R.id.expandableView);
        _relativeAutor = (RelativeLayout) findViewById(R.id.relativeAutor);
        _relativeAutor.setOnClickListener(this);
        _txtCompartirTwitter.setOnClickListener(this);
        _txtCompartir.setOnClickListener(this);

        _txtTitulo.setText(_articulo.get_titulo());
        _txtDescripcion.setText(_articulo.get_descripcion());
        _txtTexto.setText(_articulo.get_textoCompleto());
        _txtAutor.setText(_articulo.get_nombreAutor());
        _txtUsuario.setText(_articulo.get_usuario());
        Picasso.with(_imgUsuario.getContext()).
                load(_articulo.get_urlImgUsuario()).
                into(_imgUsuario, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

        _callbackManager = CallbackManager.Factory.create();
        _shareDialog = new ShareDialog(this);
        _shareDialog.registerCallback(_callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.e("FacebookSDK", "-------onSuccess-----> " + result);
            }

            @Override
            public void onCancel() {
                Log.e("FacebookSDK", "-------onCancel-----> ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("FacebookSDK", "-------onError-----> " + error);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onBackPressed---> ");
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.relativeAutor) {
            if (_expandableView.isExpanded()) {
                _expandableView.collapse();
            } else {
                _expandableView.expand();
                _expandableView.requestFocus();
                _stickyScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        _stickyScrollView.smoothScrollTo(0, _expandableView.getBottom()-1);
                    }
                });
            }
        } else if (view.getId() == R.id.txtCompartir) {
            if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("https://developers.facebook.com"))
                            .setQuote(_articulo.get_descripcion())
                            .build();
                    _shareDialog.show(content);
                }
            } else {
                Toast.makeText(view.getContext(), getResources().getString(R.string.txt_mensaje_facebook_login), Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.txtCompartirTwitter) {
            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
            TwitterAuthToken authToken = session.getAuthToken();
            String token = authToken.token;
            String secret = authToken.secret;
            if (token != null && secret != null) {
                final Intent intent = new ComposerActivity.Builder(DVActivityDetalleArticulo.this)
                        .session(session)
                        .text(_articulo.get_descripcion())
                        .hashtags("#LumieresClub")
                        .createIntent();
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
