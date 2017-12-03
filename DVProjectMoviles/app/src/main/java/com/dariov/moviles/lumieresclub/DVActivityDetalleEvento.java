package com.dariov.moviles.lumieresclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dariov.moviles.lumieresclub.models.DVArticulo;
import com.dariov.moviles.lumieresclub.utilities.DVCustomScrollView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Creado por el papu Dario Valdes  on 03/12/2017.
 */

public class DVActivityDetalleEvento extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private TextView _txtShowMap, _txtTitulo, _txtFecha, _txtHora, _txtOrganizadoPor, _txtDireccion, _txtTipoEvento, _txtDescripcion;
    private ExpandableLayout _expandableView;
    private DVCustomScrollView _nestedScroll;
    private ImageView _imgEvento;
    private DVArticulo _articulo;
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            _articulo = getIntent().getExtras().getParcelable("dvarticulo");
        }
        setContentView(R.layout.dv_evento_detalle);
        _imgEvento = (ImageView) findViewById(R.id.imgEvento);
        _txtShowMap = (TextView) findViewById(R.id.txtMapa);
        _txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        _txtFecha = (TextView) findViewById(R.id.txtFecha);
        _txtHora = (TextView) findViewById(R.id.txtHora);
        _txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        _txtTipoEvento = (TextView) findViewById(R.id.txtTipoEvento);
        _txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        _txtOrganizadoPor = (TextView) findViewById(R.id.txtOrganizadoPor);
        _nestedScroll = (DVCustomScrollView) findViewById(R.id.nestedScroll);
        _expandableView = (ExpandableLayout) findViewById(R.id.expandableView);

        _txtTitulo.setText(_articulo.get_titulo());
        _txtFecha.setText(_articulo.get_fecha());
        _txtHora.setText(_articulo.get_hora());
        _txtDireccion.setText(_articulo.get_textoCompleto());
        _txtTipoEvento.setText(_articulo.get_seccion());
        _txtDescripcion.setText(_articulo.get_descripcion());
        _txtOrganizadoPor.setText(_articulo.get_usuario());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        _txtShowMap.setOnClickListener(this);

        if (_articulo.get_urlImgArticulo() != null && !_articulo.get_urlImgArticulo().equals("")) {
            Picasso.with(_imgEvento.getContext()).load(_articulo.get_urlImgArticulo()).into(_imgEvento);
        }
    }

    @Override
    public void onBackPressed() {
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onBackPressed---> ");
        super.onBackPressed();
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(Double.parseDouble(_articulo.get_nombreAutor()), Double.parseDouble(_articulo.get_fuente()));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marke"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(15.0f);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtMapa) {
            if (_expandableView.isExpanded()) {
                _expandableView.collapse();
                _nestedScroll.setScrollingEnabled(true);
            } else {
                _expandableView.expand();
                _expandableView.requestFocus();
                _nestedScroll.setScrollingEnabled(false);
            }
        }
    }
}
