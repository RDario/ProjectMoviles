package com.dariov.moviles.lumieresclub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by DarioValdes on 04/10/2017.
 */

public class DVActivityNuevoArticulo extends AppCompatActivity {
    private EditText _editTitulo, _editTexto, _editAutor, _editFuente;
    private Spinner _spinnerAutor, _spinnerFuente, _spinnerTipo;
    private CheckBox _checkboxAutor,_checkboxFuente;
    private Button _btnGuardar, _btnPublicar;
    private TextView txtHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_nuevo_articulo);
    }
}
