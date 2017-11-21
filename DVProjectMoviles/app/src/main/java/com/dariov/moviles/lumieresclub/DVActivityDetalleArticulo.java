package com.dariov.moviles.lumieresclub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dariov.moviles.lumieresclub.models.DVArticulo;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Creado por el papu Dario Valdes  on 08/10/2017.
 */

public class DVActivityDetalleArticulo extends AppCompatActivity implements View.OnClickListener {
    private TextView _txtTitulo, _txtDescripcion, _txtTexto, _txtSeccion, _txtAutor, _txtUsuario, _txtCompartir, _txtDescripcionUser;
    private StickyScrollView _stickyScrollView;
    private ExpandableLayout _expandableView;
    private RelativeLayout _relativeAutor;
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
        _imgUsuario = (ImageView) findViewById(R.id.imgUsuario);

        _stickyScrollView = (StickyScrollView) findViewById(R.id.stickyScrollView);
        _expandableView = (ExpandableLayout) findViewById(R.id.expandableView);
        _relativeAutor = (RelativeLayout) findViewById(R.id.relativeAutor);
        _relativeAutor.setOnClickListener(this);

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
        }
    }
}
