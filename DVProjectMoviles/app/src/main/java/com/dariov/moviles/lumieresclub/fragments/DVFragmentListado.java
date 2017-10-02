package com.dariov.moviles.lumieresclub.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dariov.moviles.lumieresclub.R;
import com.dariov.moviles.lumieresclub.adapters.DVAdapterListado;
import com.dariov.moviles.lumieresclub.models.DVArticulo;

import java.util.LinkedList;

/**
 * Created by DarioValdes on 20/09/2017.
 */

public class DVFragmentListado extends Fragment implements View.OnClickListener {
    private LinkedList<DVArticulo> _listaMain;
    private DVAdapterListado _adapterListado;
    private RecyclerView _listView;
    private String title;
    private int page;

    public static DVFragmentListado newInstance(int page, String title) {
        DVFragmentListado fragmentListadoSimple = new DVFragmentListado();
        Bundle args = new Bundle();
        args.putInt("disInt", page);
        args.putString("disTitle", title);
        fragmentListadoSimple.setArguments(args);
        return fragmentListadoSimple;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("disInt", 0);
        title = getArguments().getString("disTitle");
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
        if (_adapterListado != null && _listaMain != null) {
            _listaMain.clear();
            for (int i = 0; i < 3; i++) {
                _listaMain.add(new DVArticulo("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua...",
                        "12 de Septiembre de 2017",
                        "Miklovish"));
            }
            _adapterListado.setListaArticulo(_listaMain);
            LinearLayoutManager llm = new LinearLayoutManager(_listView.getContext());
            _listView.setLayoutManager(llm);
            _listView.setAdapter(_adapterListado);
        }
    }

    @Override
    public void onClick(View view) {
    }
}
