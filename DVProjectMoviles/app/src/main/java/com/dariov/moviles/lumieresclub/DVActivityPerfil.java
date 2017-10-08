package com.dariov.moviles.lumieresclub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Creado por el papu Dario Valdes  on 08/10/2017.
 */

public class DVActivityPerfil extends AppCompatActivity {
    private TextView _txtTitulo, _txtSubtitulo, _txtTexto, _txtSeccion, _txtNombreAutor, _txtDescripcionAutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_mi_perfil);
    }

    @Override
    public void onBackPressed() {
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onBackPressed---> ");
        super.onBackPressed();
        finish();
    }
}
