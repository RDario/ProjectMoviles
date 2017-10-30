package com.dariov.moviles.lumieresclub.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Creado por el papu Dario Valdes  on 28/10/2017.
 */

public class DVItemMenu implements Parcelable {
    private String _idItem;
    private String _titulo;
    private String _urlDatos;

    public DVItemMenu(JSONObject jsonObject) {
        set_idItem(jsonObject.optString("identificador"));
        set_titulo(jsonObject.optString("titulo"));
        set_urlDatos(jsonObject.optString("url"));
    }

    public String get_idItem() {
        return _idItem;
    }

    private void set_idItem(String _idItem) {
        this._idItem = _idItem;
    }

    public String get_titulo() {
        return _titulo;
    }

    private void set_titulo(String _titulo) {
        this._titulo = _titulo;
    }

    public String get_urlDatos() {
        return _urlDatos;
    }

    private void set_urlDatos(String _urlDatos) {
        this._urlDatos = _urlDatos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_idItem);
        parcel.writeString(_titulo);
        parcel.writeString(_urlDatos);
    }

    protected DVItemMenu(Parcel in) {
        _idItem = in.readString();
        _titulo = in.readString();
        _urlDatos = in.readString();
    }

    public static final Creator<DVItemMenu> CREATOR = new Creator<DVItemMenu>() {
        @Override
        public DVItemMenu createFromParcel(Parcel in) {
            return new DVItemMenu(in);
        }

        @Override
        public DVItemMenu[] newArray(int size) {
            return new DVItemMenu[size];
        }
    };
}
