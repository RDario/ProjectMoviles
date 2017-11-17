package com.dariov.moviles.lumieresclub.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.DVActivityDetalleArticulo;
import com.dariov.moviles.lumieresclub.R;
import com.dariov.moviles.lumieresclub.adapters.DVAdapterListado;
import com.dariov.moviles.lumieresclub.interfaces.DVListenerMensajePerfil;
import com.dariov.moviles.lumieresclub.models.DVArticulo;
import com.dariov.moviles.lumieresclub.models.DVItemMenu;
import com.dariov.moviles.lumieresclub.utilities.DVHiloDescarga;
import com.dariov.moviles.lumieresclub.utilities.DVSQLDataBase;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URI;
import java.util.LinkedList;

/**
 * Created by DarioValdes on 20/09/2017.
 */

public class DVFragmentListado extends Fragment implements View.OnClickListener, DVHiloDescarga.DVListenerHiloDescarga {
    private DVListenerMensajePerfil _listenerMensajePerfil;
    private LinkedList<DVArticulo> _listaMain;
    private DVAdapterListado _adapterListado;
    public String title, page, url, _idUser;
    private DVHiloDescarga _hiloDescarga;
    private RecyclerView _listView;

    public static DVFragmentListado newInstance(Object objeto) {
        DVFragmentListado fragmentListadoSimple = new DVFragmentListado();
        if (objeto instanceof DVItemMenu) {
            Bundle args = new Bundle();
            args.putString("disId", ((DVItemMenu) objeto).get_idItem());
            args.putString("disTitle", ((DVItemMenu) objeto).get_titulo());
            args.putString("disUrl", ((DVItemMenu) objeto).get_urlDatos());
            args.putInt("isFromperfil", 0);
            fragmentListadoSimple.setArguments(args);
        }
        return fragmentListadoSimple;
    }

    public static DVFragmentListado newInstanceFromPerfil(String idUsuario, DVListenerMensajePerfil listenerMensajePerfil) {
        DVFragmentListado fragmentListadoSimple = new DVFragmentListado();
        Bundle args = new Bundle();
        args.putString("disId", idUsuario);
        args.putInt("isFromPerfil", 1);
        fragmentListadoSimple.setArguments(args);
        fragmentListadoSimple.setListenerMensaje(listenerMensajePerfil);
        return fragmentListadoSimple;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getInt("isFromPerfil") == 1) {
            _idUser = getArguments().getString("disId");
        } else {
            page = getArguments().getString("disId", "");
            title = getArguments().getString("disTitle");
            url = getArguments().getString("disUrl");
        }
        _listaMain = new LinkedList<>();
        _adapterListado = new DVAdapterListado();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dv_fragment_listadosimple, container, false);
        _listView = view.findViewById(R.id.listViewMain);
        if (_idUser != null && _idUser.equals("1")) {
            consulta(getActivity());
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onDestroy---> ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onDestroy---> ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onDestroy---> ");
    }

    @Override
    public void onHiloDescargaSuccess(String res) {
        if (res != null && !res.equals("")) {
            if (_adapterListado != null && _listaMain != null) {
                _listaMain.clear();
                try {
                    JSONArray jsonArray = new JSONArray(res);
                    if (jsonArray.length() > 0) {
                        for (int i=0;i < jsonArray.length(); i++) {
                            _listaMain.add(new DVArticulo(jsonArray.optJSONObject(i)));
                        }
                        _adapterListado.setListaArticulo(_listaMain);
                        _adapterListado.setOnClickListener(this);
                        _adapterListado.setContext(getActivity());
                        LinearLayoutManager llm = new LinearLayoutManager(_listView.getContext());
                        _listView.setLayoutManager(llm);
                        _listView.setAdapter(_adapterListado);
                    }
                } catch (JSONException e) {
                    Log.e(this.getClass().getSimpleName(), "-----JSON-Exception---> "+ e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), DVActivityDetalleArticulo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent. FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivityForResult(intent, 0);
    }

    public void setListenerMensaje(DVListenerMensajePerfil listenerMensaje) {
        this._listenerMensajePerfil = listenerMensaje;
    }

    public void DescargaInformacion(String url) {
        if (!url.equals("")) {
            if (_hiloDescarga == null) {
                _hiloDescarga = new DVHiloDescarga(this);
                _hiloDescarga.execute(URI.create(url));
            }
        }
    }

    public void consulta(Context context) {
        DVSQLDataBase admin = new DVSQLDataBase(context, "databaseMoviles.db", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM articulo", null);
        if (fila.moveToFirst()) {
            _listaMain.add(new DVArticulo(
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
            while (fila.moveToNext()) {
                _listaMain.add(new DVArticulo(
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

                Log.e(getClass().getSimpleName(), " -------1----> " + fila.getString(0));
                Log.e(getClass().getSimpleName(), " -------2----> " + fila.getString(1));
                Log.e(getClass().getSimpleName(), " -------3----> " + fila.getString(2));
                Log.e(getClass().getSimpleName(), " -------4----> " + fila.getString(3));
                Log.e(getClass().getSimpleName(), " -------5----> " + fila.getString(4));
                Log.e(getClass().getSimpleName(), " -------6----> " + fila.getString(5));
                Log.e(getClass().getSimpleName(), " -------7----> " + fila.getString(6));
                Log.e(getClass().getSimpleName(), " -------8----> " + fila.getString(7));
                Log.e(getClass().getSimpleName(), " -------9----> " + fila.getString(8));
                Log.e(getClass().getSimpleName(), " -------10----> " + fila.getString(9));
                Log.e(getClass().getSimpleName(), " -------11----> " + fila.getString(10));
                Log.e(getClass().getSimpleName(), " -------12----> " + fila.getString(11));
                Log.e(getClass().getSimpleName(), " -------13----> " + fila.getString(12));
            }
            _adapterListado.setListaArticulo(_listaMain);
            _adapterListado.setOnClickListener(this);
            _adapterListado.setContext(getActivity());
            LinearLayoutManager llm = new LinearLayoutManager(_listView.getContext());
            _listView.setLayoutManager(llm);
            _listView.setAdapter(_adapterListado);
        } else {
            _listenerMensajePerfil.onMostrarMensaje();
        }
        fila.close();
        bd.close();
    }
}
