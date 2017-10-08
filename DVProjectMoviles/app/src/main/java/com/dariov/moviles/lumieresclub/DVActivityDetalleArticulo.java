package com.dariov.moviles.lumieresclub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Creado por el papu Dario Valdes  on 08/10/2017.
 */

public class DVActivityDetalleArticulo extends AppCompatActivity implements View.OnClickListener {
    private TextView _txtTitulo, _txtSubtitulo, _txtTexto, _txtSeccion, _txtNombreAutor, _txtDescripcionAutor;
    private StickyScrollView _stickyScrollView;
    private ExpandableLayout _expandableView;
    private RelativeLayout _relativeAutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_articulo_detalle);
        _stickyScrollView = (StickyScrollView) findViewById(R.id.stickyScrollView);
        _expandableView = (ExpandableLayout) findViewById(R.id.expandableView);
        _relativeAutor = (RelativeLayout) findViewById(R.id.relativeAutor);
        _txtDescripcionAutor = (TextView) findViewById(R.id.txtDescripcionAutor);
        _relativeAutor.setOnClickListener(this);
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
