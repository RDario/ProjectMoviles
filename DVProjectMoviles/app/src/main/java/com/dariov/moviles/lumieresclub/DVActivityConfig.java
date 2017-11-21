package com.dariov.moviles.lumieresclub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Creado por el papu Dario Valdes  on 29/10/2017.
 */

public class DVActivityConfig extends AppCompatActivity implements View.OnClickListener {
    private CheckBox _checkEspanol, _checkIngles;
    private SharedPreferences sharedPref;
    private ToggleButton _toggleButton;
    private String _idiom, _saveTitles;
    private Button _btnGuardar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_config);
        _toggleButton = (ToggleButton) findViewById(R.id.toggleTitulos);
        _checkEspanol = (CheckBox) findViewById(R.id.checkEspanol);
        _checkIngles = (CheckBox) findViewById(R.id.checkIngles);
        _btnGuardar = (Button) findViewById(R.id.btnGuardar);
        _btnGuardar.setOnClickListener(this);
        _checkEspanol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    _checkIngles.setChecked(false);
                    _idiom = "Spanish";
                }
            }
        });
        _checkIngles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    _checkEspanol.setChecked(false);
                    _idiom = "English";
                }
            }
        });
        sharedPref = getSharedPreferences("preferenceConfig", Context.MODE_PRIVATE);
        String prefeIdioma = sharedPref.getString("prefeIdioma", "");
        String prefeTitulos = sharedPref.getString("prefeTitulos", "");

        Log.e("ActivityConfig", " --------prefetitulos-----> " + prefeTitulos);
        Log.e("ActivityConfig", " --------prefeidioma-----> " + prefeIdioma);

        if (prefeIdioma.equals("")) {
            _checkEspanol.setChecked(true);
            _checkIngles.setChecked(false);
            _idiom = "Spanish";
        } else if (prefeIdioma.equals("Spanish")) {
            _checkEspanol.setChecked(true);
            _checkIngles.setChecked(false);
            _idiom = "Spanish";
        } else if (prefeIdioma.equals("English")) {
            _checkIngles.setChecked(true);
            _checkEspanol.setChecked(false);
            _idiom = "English";
        }

        if (prefeTitulos.equals("")) {
            _toggleButton.setChecked(false);
        } else if (prefeTitulos.equals("Si")) {
            _toggleButton.setChecked(true);
            _saveTitles = "Si";
        } else if (prefeTitulos.equals("No")) {
            _toggleButton.setChecked(false);
            _saveTitles = "No";
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGuardar) {
            if (_toggleButton.isChecked()) {
                _saveTitles = "Si";
            } else {
                _saveTitles = "No";
            }
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("prefeIdioma", _idiom);
            editor.putString("prefeTitulos", _saveTitles);
            editor.apply();
            Log.e("ActivityConfig", " --------prefetitulos-guardar----> " + _saveTitles);
            Log.e("ActivityConfig", " --------prefetitulos-guardar----> " + _idiom);
            Toast.makeText(view.getContext(), "Se guardó la información", Toast.LENGTH_LONG).show();
        }
    }
}
