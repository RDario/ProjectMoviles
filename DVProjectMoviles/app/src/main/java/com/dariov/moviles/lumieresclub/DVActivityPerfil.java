package com.dariov.moviles.lumieresclub;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.adapters.DVPagerAdapterFragments;
import com.dariov.moviles.lumieresclub.fragments.DVFragmentListado;
import com.dariov.moviles.lumieresclub.models.DVArticulo;
import com.dariov.moviles.lumieresclub.utilities.DVSQLDataBase;

import java.util.LinkedList;

/**
 * Creado por el papu Dario Valdes  on 08/10/2017.
 */

public class DVActivityPerfil extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TextView _txtTitulo, _txtSubtitulo, _txtTexto, _txtSeccion, _txtNombreAutor, _txtDescripcionAutor;
    private DVPagerAdapterFragments _pagerAdapterFragments;
    private LinkedList<DVFragmentListado> _listaFrags;
    private LinkedList<DVArticulo> _listaArticulos;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_mi_perfil);
        _tabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);
        _viewPager = (ViewPager) findViewById(R.id.viewPagerMain);

        _tabLayout.addOnTabSelectedListener(this);
        _pagerAdapterFragments = new DVPagerAdapterFragments(getSupportFragmentManager());
        //TODO Haciendo pedo de articulos guardados
        _listaFrags = new LinkedList<>();
        _tabLayout.addTab(_tabLayout.newTab().setText("Últimas publicaciones"));
        _tabLayout.addTab(_tabLayout.newTab().setText("Borradores"));

        _viewPager.setAdapter(_pagerAdapterFragments);
        _viewPager.setOffscreenPageLimit(_tabLayout.getTabCount());
        _tabLayout.setupWithViewPager(_viewPager);
    }

    @Override
    public void onBackPressed() {
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onBackPressed---> ");
        super.onBackPressed();
        finish();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void consulta() {
        DVSQLDataBase admin = new DVSQLDataBase(this, "databaseMoviles.db", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM articulo WHERE idUsuario=1", null);
        if (fila.moveToFirst()) {
            _listaArticulos = new LinkedList<>();
            _listaArticulos.add(new DVArticulo(
                    fila.getString(0),
                    fila.getString(1),
                    fila.getString(2),
                    fila.getString(3),
                    fila.getString(4),
                    fila.getString(5),
                    fila.getString(6),
                    fila.getString(7),
                    fila.getString(8),
                    fila.getString(9),
                    fila.getString(10),
                    fila.getString(11),
                    fila.getString(12)));
            Log.e("SQLite", "columna_uno---> " + fila.getString(0));
            Log.e("SQLite", "columna_dos---> " + fila.getString(1));
            Log.e("SQLite", "columna_tres--> " + fila.getString(2));
            Log.e("SQLite", "columna_cuatro--> " + fila.getString(3));
            Log.e("SQLite", "columna_cinco--> " + fila.getString(4));
            Log.e("SQLite", "columna_seis--> " + fila.getString(5));
            Log.e("SQLite", "columna_siete--> " + fila.getString(6));
            Log.e("SQLite", "columna_ocho--> " + fila.getString(7));
            Log.e("SQLite", "columna_nueve--> " + fila.getString(8));
            Log.e("SQLite", "columna_diez--> " + fila.getString(9));
            Log.e("SQLite", "columna_once--> " + fila.getString(10));
            Log.e("SQLite", "columna_doce--> " + fila.getString(11));
            Log.e("SQLite", "columna_trece--> " + fila.getString(12));
        } else {
            Toast.makeText(this, "No existe registro puñetas", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        bd.close();
    }
}
