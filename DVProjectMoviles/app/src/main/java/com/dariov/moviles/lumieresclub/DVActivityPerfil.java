package com.dariov.moviles.lumieresclub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dariov.moviles.lumieresclub.adapters.DVPagerAdapterFragments;
import com.dariov.moviles.lumieresclub.fragments.DVFragmentListado;
import com.dariov.moviles.lumieresclub.interfaces.DVListenerMensajePerfil;

import java.util.LinkedList;

/**
 * Creado por el papu Dario Valdes  on 08/10/2017.
 */

public class DVActivityPerfil extends AppCompatActivity implements TabLayout.OnTabSelectedListener, DVListenerMensajePerfil {
    private DVPagerAdapterFragments _pagerAdapterFragments;
    private LinkedList<DVFragmentListado> _listaFrags;
    private TextView _txtMensajeBorrador;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_mi_perfil);
        _tabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);
        _viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        _txtMensajeBorrador = (TextView) findViewById(R.id.txtMensajeBorrador);
        _tabLayout.addOnTabSelectedListener(this);
        _pagerAdapterFragments = new DVPagerAdapterFragments(getSupportFragmentManager());
        _listaFrags = new LinkedList<>();
        _tabLayout.addTab(_tabLayout.newTab().setText("Borradores"));
        _listaFrags.add(DVFragmentListado.newInstanceFromPerfil("1", this));
        _pagerAdapterFragments.setListAdapter(_listaFrags);
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

    @Override
    public void onMostrarMensaje() {
        _txtMensajeBorrador.setVisibility(View.VISIBLE);
    }
}
