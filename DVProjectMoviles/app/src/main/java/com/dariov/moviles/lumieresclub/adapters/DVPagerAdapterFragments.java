package com.dariov.moviles.lumieresclub.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

/**
 * Created by DarioValdes on 20/09/2017.
 */

public class DVPagerAdapterFragments extends FragmentPagerAdapter {
    private LinkedList<Fragment> _listaFrags;

    public DVPagerAdapterFragments(FragmentManager fm) {
        super(fm);
    }

    public void setListAdapter(LinkedList<Fragment> lista) {
        this._listaFrags = lista;
    }

    @Override
    public Fragment getItem(int position) {
        return _listaFrags.get(position);
    }

    @Override
    public int getCount() {
        if (_listaFrags != null) {
            return _listaFrags.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
