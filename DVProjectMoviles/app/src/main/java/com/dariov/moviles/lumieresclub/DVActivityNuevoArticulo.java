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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.utilities.DVLoginSingleton;
import com.dariov.moviles.lumieresclub.utilities.DVHiloDescarga;
import com.dariov.moviles.lumieresclub.utilities.DVSQLDataBase;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;

import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by DarioValdes on 04/10/2017.
 */

public class DVActivityNuevoArticulo extends AppCompatActivity implements View.OnClickListener {
    private String[] _arrayFuentes = {"POEMA", "LIBRO", "CANCION", "MANUSCRITO", "CITA TEXTUAL"};
    private EditText _editTitulo, _editTexto, _editAutor, _editFuente, _editDescripcion;
    private Spinner _spinnerAutor, _spinnerFuente, _spinnerTipo;
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
        ArrayList<String> listaAutores = new ArrayList<>();
        listaAutores.add("Selecciona uno...");
        ArrayList<String> listaFuentes = new ArrayList<>();
        listaFuentes.add("Selecciona uno...");

        DVSQLDataBase admin = new DVSQLDataBase(getBaseContext(), "databaseMoviles.db", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM autores", null);
        if (fila.moveToFirst()) {
            do {
                listaAutores.add(fila.getString(0));
            } while (fila.moveToNext());
        }
        Cursor filaDos = bd.rawQuery("SELECT * FROM fuentes", null);
        if (filaDos.moveToFirst()) {
            do {
                listaFuentes.add(filaDos.getString(0));
            } while (filaDos.moveToNext());
        }
        fila.close();
        filaDos.close();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(_spinnerTipo.getContext(), android.R.layout.simple_expandable_list_item_1, _arrayFuentes);
        _spinnerTipo.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapterAutor = new ArrayAdapter<>(_spinnerAutor.getContext(), android.R.layout.simple_expandable_list_item_1, listaAutores);
        _spinnerAutor.setAdapter(arrayAdapterAutor);
        ArrayAdapter<String> arrayAdapterFuente = new ArrayAdapter<>(_spinnerFuente.getContext(), android.R.layout.simple_expandable_list_item_1, listaFuentes);
        _spinnerFuente.setAdapter(arrayAdapterFuente);

        _spinnerFuente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                _editFuente.setText("");
                _editFuente.setText(_spinnerFuente.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        _spinnerAutor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                _editAutor.setText("");
                _editAutor.setText(_spinnerAutor.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        String txtAutor = _editAutor.getText().toString();
        String txtFuente = _editFuente.getText().toString();
        String txtTitulo = _editTitulo.getText().toString();
        String txtTexto = _editTexto.getText().toString();
        String txtTipo = _spinnerTipo.getSelectedItem().toString();
        String txtDescrip = _editDescripcion.getText().toString();
        Date datexx = new Date();
        String stringDate = DateFormat.getDateInstance().format(datexx);
        String stringDateTime = DateFormat.getTimeInstance().format(datexx);
        if (view.getId() == R.id.btnGuardar) {
            if (DVLoginSingleton._correo != null && DVLoginSingleton._contrasenia != null) {
                SharedPreferences sharedPref = getSharedPreferences("preferenceConfig", Context.MODE_PRIVATE);
                String prefeTitulos = sharedPref.getString("prefeTitulos", "");
                if (prefeTitulos.equals("Si")) {
                    DVSQLDataBase _sqlitehelper = new DVSQLDataBase(view.getContext(),"databaseMoviles.db", null, 1);
                    SQLiteDatabase database = _sqlitehelper.getWritableDatabase();
                    ContentValues registro =  new ContentValues();
                    ContentValues registroDos =  new ContentValues();

                    registro.put(DVSQLDataBase.COLUMN_AUTOR_NOMBRE, txtAutor);
                    database.insert(DVSQLDataBase.TABLE_AUTORES,null, registro);

                    registroDos.put(DVSQLDataBase.COLUMN_FUENTES_NOMBRE, txtFuente);
                    database.insert(DVSQLDataBase.TABLE_FUENTES,null, registroDos);
                }

                DVSQLDataBase _sqlitehelper = new DVSQLDataBase(view.getContext(),"databaseMoviles.db", null, 1);
                SQLiteDatabase database = _sqlitehelper.getWritableDatabase();
                ContentValues _registro =  new ContentValues();
                if (_editTitulo != null && !_editTitulo.getText().toString().equals("")) {
                    _registro.put(DVSQLDataBase.COLUMN_TITULO, txtTitulo);
                    _registro.put(DVSQLDataBase.COLUMN_DESCRIPCION, txtDescrip);
                    _registro.put(DVSQLDataBase.COLUMN_TEXTO, txtTexto);
                    _registro.put(DVSQLDataBase.COLUMN_AUTOR, txtAutor);
                    _registro.put(DVSQLDataBase.COLUMN_TIPO_FUENTE, txtTipo);
                    _registro.put(DVSQLDataBase.COLUMN_FUENTE, txtFuente);
                    _registro.put(DVSQLDataBase.COLUMN_FECHA, stringDate);
                    _registro.put(DVSQLDataBase.COLUMN_HORA, stringDateTime);
                    _registro.put(DVSQLDataBase.COLUMN_ID_USUARIO, DVLoginSingleton._id);
                    _registro.put(DVSQLDataBase.COLUMN_NOMBRE_USUARIO, DVLoginSingleton._nombre);
                    database.insert(DVSQLDataBase.TABLE_ARTICULO,null, _registro);
                    Toast.makeText(view.getContext(), "Se guardó la información en el borrador", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Ingrese un título al menos", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(view.getContext(), "Inicia sesión para continuar", Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.btnPublicar) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("txtTitulo", txtTitulo);
            hashMap.put("txtDescripcion", txtDescrip);
            hashMap.put("txtTextoCompleto", txtTexto);
            hashMap.put("txtSeccion", "");
            hashMap.put("txtAutor", txtAutor);
            hashMap.put("txtFuente", txtFuente);
            hashMap.put("txtTipoArticulo", txtTipo);
            hashMap.put("txtIdUsuario", DVLoginSingleton._id);
            hashMap.put("txtUsuario", DVLoginSingleton._nombre + " " + DVLoginSingleton._apellidoP + " " + DVLoginSingleton._apellidoM);
            hashMap.put("txtFecha", stringDate);
            hashMap.put("txtHora", stringDateTime);
            hashMap.put("txtUrlImg", "");
            DVHiloDescarga adHiloDescarga = new DVHiloDescarga(new DVHiloDescarga.DVListenerHiloDescarga(){
                @Override
                public void onHiloDescargaSuccess(String res) {
                    Log.e("HiloDescargaImagen","------res-------> " + res);
                    Toast.makeText(getBaseContext(), "Se subió publicación exitosamente", Toast.LENGTH_LONG).show();
                }
            }, hashMap);
            adHiloDescarga.execute(URI.create("http://www.elevation.com.mx/pages/pruebas/moviles/upload_article.php"));
        }
    }
}
