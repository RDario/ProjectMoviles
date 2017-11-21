package com.dariov.moviles.lumieresclub.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Creado por el papu Dario Valdes  on 20/11/2017.
 */

public class DVUsuario implements Parcelable {
    private String _idUsuario;
    private String _nombre;
    private String _apellidoP;
    private String _apellidoM;
    private String _tipoUsuario;
    private String _correo;
    private String _contrasenia;
    private String _urlImg;

    public DVUsuario(JSONObject jsonObject) {
        set_idUsuario(jsonObject.optString("idUsuario", ""));
        set_nombre(jsonObject.optString("nombre", ""));
        set_apellidoP(jsonObject.optString("apellidoP", ""));
        set_apellidoM(jsonObject.optString("apellidoM", ""));
        set_tipoUsuario(jsonObject.optString("tipoU", ""));
        set_correo(jsonObject.optString("correo", ""));
        set_contrasenia(jsonObject.optString("contrasenia", ""));
        set_urlImg(jsonObject.optString("url_img_autor", ""));
    }

    public String get_idUsuario() {
        return _idUsuario;
    }

    private void set_idUsuario(String _idUsuario) {
        this._idUsuario = _idUsuario;
    }

    public String get_nombre() {
        return _nombre;
    }

    private void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_apellidoP() {
        return _apellidoP;
    }

    private void set_apellidoP(String _apellidoP) {
        this._apellidoP = _apellidoP;
    }

    public String get_apellidoM() {
        return _apellidoM;
    }

    private void set_apellidoM(String _apellidoM) {
        this._apellidoM = _apellidoM;
    }

    public String get_tipoUsuario() {
        return _tipoUsuario;
    }

    private void set_tipoUsuario(String _tipoUsuario) {
        this._tipoUsuario = _tipoUsuario;
    }

    public String get_correo() {
        return _correo;
    }

    private void set_correo(String _correo) {
        this._correo = _correo;
    }

    public String get_contrasenia() {
        return _contrasenia;
    }

    private void set_contrasenia(String _contrasenia) {
        this._contrasenia = _contrasenia;
    }

    public String get_urlImg() {
        return _urlImg;
    }

    private void set_urlImg(String _urlImg) {
        this._urlImg = _urlImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_idUsuario);
        parcel.writeString(_nombre);
        parcel.writeString(_apellidoP);
        parcel.writeString(_apellidoM);
        parcel.writeString(_tipoUsuario);
        parcel.writeString(_correo);
        parcel.writeString(_contrasenia);
        parcel.writeString(_urlImg);
    }

    protected DVUsuario(Parcel in) {
        _idUsuario = in.readString();
        _nombre = in.readString();
        _apellidoP = in.readString();
        _apellidoM = in.readString();
        _tipoUsuario = in.readString();
        _correo = in.readString();
        _contrasenia = in.readString();
        _urlImg = in.readString();
    }

    public static final Creator<DVUsuario> CREATOR = new Creator<DVUsuario>() {
        @Override
        public DVUsuario createFromParcel(Parcel in) {
            return new DVUsuario(in);
        }

        @Override
        public DVUsuario[] newArray(int size) {
            return new DVUsuario[size];
        }
    };
}
