package com.dariov.moviles.lumieresclub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dariov.moviles.lumieresclub.DVActivityDetalleArticulo;
import com.dariov.moviles.lumieresclub.R;
import com.dariov.moviles.lumieresclub.adapters.DVAdapterListado;
import com.dariov.moviles.lumieresclub.models.DVArticulo;
import com.dariov.moviles.lumieresclub.models.DVItemMenu;
import com.dariov.moviles.lumieresclub.utilities.DVHiloDescarga;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URI;
import java.util.LinkedList;

/**
 * Created by DarioValdes on 20/09/2017.
 */

public class DVFragmentListado extends Fragment implements View.OnClickListener, DVHiloDescarga.DVListenerHiloDescarga {
    private LinkedList<DVArticulo> _listaMain;
    private DVAdapterListado _adapterListado;
    private DVHiloDescarga _hiloDescarga;
    public String title, page, url;
    private RecyclerView _listView;

    public static DVFragmentListado newInstance(Object objeto) {
        DVFragmentListado fragmentListadoSimple = new DVFragmentListado();
        if (objeto instanceof DVItemMenu) {
            Bundle args = new Bundle();
            args.putString("disId", ((DVItemMenu) objeto).get_idItem());
            args.putString("disTitle", ((DVItemMenu) objeto).get_titulo());
            args.putString("disUrl", ((DVItemMenu) objeto).get_urlDatos());
            fragmentListadoSimple.setArguments(args);
        }
        return fragmentListadoSimple;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getString("disId", "");
        title = getArguments().getString("disTitle");
        url = getArguments().getString("disUrl");
        _listaMain = new LinkedList<>();
        _adapterListado = new DVAdapterListado();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dv_fragment_listadosimple, container, false);
        _listView = view.findViewById(R.id.listViewMain);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /* if (_hiloDescarga == null) {
            _hiloDescarga = new DVHiloDescarga(this);
            _hiloDescarga.execute(URI.create(url));
        } else {
            _hiloDescarga.execute(URI.create(url));
        } */
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

    public void DescargaInformacion(String url) {
        if (!url.equals("")) {
            if (_hiloDescarga == null) {
                _hiloDescarga = new DVHiloDescarga(this);
                _hiloDescarga.execute(URI.create(url));
            }
        }
    }
}
