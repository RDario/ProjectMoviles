package com.dariov.moviles.lumieresclub;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.utilities.DVSQLDataBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DarioValdes on 04/10/2017.
 */

public class DVActivityNuevoArticulo extends AppCompatActivity implements View.OnClickListener {
    private String[] _arrayFuentes = {"POEMA", "LIBRO", "CANCION", "MANUSCRITO", "CITA TEXTUAL"};
    private EditText _editTitulo, _editTexto, _editAutor, _editFuente, _editDescripcion;
    private Spinner _spinnerAutor, _spinnerFuente, _spinnerTipo;
    private String[] _arrayOtros = {"Selecciona uno..."};
    private CheckBox _checkboxAutor,_checkboxFuente;
    private Button _btnGuardar, _btnPublicar;
    private TextView _txtHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_nuevo_articulo);
        _editTitulo = (EditText) findViewById(R.id.editTitulo);
        _editTexto = (EditText) findViewById(R.id.editTexto);
        _editAutor = (EditText) findViewById(R.id.editAutor);
        _editFuente = (EditText) findViewById(R.id.editFuente);
        _editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        _spinnerAutor = (Spinner) findViewById(R.id.spinnerAutor);
        _spinnerFuente = (Spinner) findViewById(R.id.spinnerFuente);
        _spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
        _btnGuardar = (Button) findViewById(R.id.btnGuardar);
        _btnPublicar = (Button) findViewById(R.id.btnPublicar);
        _txtHeader = (TextView) findViewById(R.id.txtHeader);
        _btnGuardar.setOnClickListener(this);
        _btnPublicar.setOnClickListener(this);

        SharedPreferences sharedPref = getSharedPreferences("prefeArticulo", Context.MODE_PRIVATE);
        String prefeAutor = sharedPref.getString("prefeAutor", "");
        String prefeFuente = sharedPref.getString("prefeFuente", "");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(_spinnerTipo.getContext(), android.R.layout.simple_expandable_list_item_1, _arrayFuentes);
        _spinnerTipo.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapterAutor = new ArrayAdapter<>(_spinnerAutor.getContext(), android.R.layout.simple_expandable_list_item_1, _arrayOtros);
        _spinnerAutor.setAdapter(arrayAdapterAutor);
        ArrayAdapter<String> arrayAdapterFuente = new ArrayAdapter<>(_spinnerFuente.getContext(), android.R.layout.simple_expandable_list_item_1, _arrayOtros);
        _spinnerFuente.setAdapter(arrayAdapterFuente);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGuardar) {
            DVSQLDataBase _sqlitehelper = new DVSQLDataBase(view.getContext(),"databaseMoviles.db", null, 1);
            SQLiteDatabase database = _sqlitehelper.getWritableDatabase();
            ContentValues _registro =  new ContentValues();
            String txtAutor = "";
            String txtFuente = "";
            String txtTitulo = "";
            String txtTexto = "";
            String txtTipo = "";
            String txtDescrip = "";
            if (_editTitulo != null && !_editTitulo.getText().toString().equals("")) {
                txtTitulo = _editTitulo.getText().toString();
                txtTexto = _editTexto.getText().toString();
                txtTipo = _spinnerTipo.getSelectedItem().toString();
                txtDescrip = _editDescripcion.getText().toString();
                if (_editAutor != null && !_editAutor.getText().toString().equals("") && _editFuente != null && !_editFuente.getText().toString().equals("")) {
                    txtAutor = _editAutor.getText().toString();
                    txtFuente = _editFuente.getText().toString();
                } else {
                    txtAutor = _spinnerAutor.getPrompt().toString();
                    txtFuente = _spinnerFuente.getPrompt().toString();
                }
                Date datexx = new Date();
                String stringDate = DateFormat.getDateInstance().format(datexx);
                String stringDateTime = DateFormat.getTimeInstance().format(datexx);
                _registro.put(DVSQLDataBase.COLUMN_TITULO, txtTitulo);
                _registro.put(DVSQLDataBase.COLUMN_DESCRIPCION, txtDescrip);
                _registro.put(DVSQLDataBase.COLUMN_TEXTO, txtTexto);
                _registro.put(DVSQLDataBase.COLUMN_AUTOR, txtAutor);
                _registro.put(DVSQLDataBase.COLUMN_TIPO_FUENTE, txtTipo);
                _registro.put(DVSQLDataBase.COLUMN_FUENTE, txtFuente);
                _registro.put(DVSQLDataBase.COLUMN_FECHA, stringDate);
                _registro.put(DVSQLDataBase.COLUMN_HORA, stringDateTime);
                _registro.put(DVSQLDataBase.COLUMN_ID_USUARIO, "1");
                _registro.put(DVSQLDataBase.COLUMN_NOMBRE_USUARIO, "Dario Val Ban");
                database.insert(DVSQLDataBase.TABLE_ARTICULO,null, _registro);
                Toast.makeText(view.getContext(), "Se guardó la información en el borrador", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(view.getContext(), "Ingrese un título al menos", Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.btnPublicar) {
        }
    }
}
