package com.dariov.moviles.lumieresclub.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DarioValdes on 02/10/2017.
 */

public class DVArticulo implements Parcelable {
    private String _idArticulo;
    private String _titulo;
    private String _descripcion;
    private String _textoCompleto;
    private String _seccion;
    private String _tipoArticulo;
    private String _fuente;
    private String _fecha;
    private String _hora;
    private String _nombreAutor;
    private String _idUsuario;
    private String _usuario;
    private String _urlImgArticulo;
    private String _urlImgUsuario;
    private String _urlArticulo;
    private String _urlPdf;

    public DVArticulo() {}

    public DVArticulo(JSONObject jsonObject) {
        if (jsonObject != null) {
            set_idArticulo(jsonObject.optString("identificador"));
            set_titulo(jsonObject.optString("titulo"));
            set_descripcion(jsonObject.optString("descripcion"));
            set_textoCompleto(jsonObject.optString("texto"));
            set_seccion(jsonObject.optString("seccion"));
            set_tipoArticulo(jsonObject.optString("tipo"));
            set_fecha(jsonObject.optString("fecha"));
            set_hora(jsonObject.optString("hora"));
            set_nombreAutor(jsonObject.optString("autor"));
            set_idAutor(jsonObject.optString("id_usuario"));
            set_usuario(jsonObject.optString("usuario"));
            set_urlImgArticulo(jsonObject.optString("url_img_articulo"));
            set_urlImgUsuario("http://www.elevation.com.mx/pages/pruebas/moviles/images/placeholder_user.jpg");
        }
    }

    public DVArticulo(String ide, String title, String summary, String textComplete,
                      String secc, String type, String source, String date, String datetime,
                      String nomAuthor, String user,  String idUser, String urlArticle, String urlImgUser) {
            set_idArticulo(ide);
            set_titulo(title);
            set_descripcion(summary);
            set_textoCompleto(textComplete);
            set_seccion(secc);
            set_tipoArticulo(type);
            set_fuente(source);
            set_fecha(date);
            set_hora(datetime);
            set_nombreAutor(nomAuthor);
            set_usuario(user);
            set_idAutor(idUser);
            set_urlImgArticulo(urlArticle);
            set_urlImgUsuario("http://www.elevation.com.mx/pages/pruebas/moviles/images/placeholder_user.jpg");
    }

    public String get_idArticulo() {
        return _idArticulo;
    }

    private void set_idArticulo(String _idArticulo) {
        this._idArticulo = _idArticulo;
    }

    public String get_titulo() {
        return _titulo;
    }

    private void set_titulo(String _titulo) {
        this._titulo = _titulo;
    }

    public String get_descripcion() {
        return _descripcion;
    }

    private void set_descripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public String get_seccion() {
        return _seccion;
    }

    private void set_seccion(String _seccion) {
        this._seccion = _seccion;
    }

    public String get_tipoArticulo() {
        return _tipoArticulo;
    }

    private void set_tipoArticulo(String _tipoArticulo) {
        this._tipoArticulo = _tipoArticulo;
    }

    public String get_fecha() {
        return _fecha;
    }

    private void set_fecha(String _fecha) {
        this._fecha = _fecha;
    }

    public String get_nombreAutor() {
        return _nombreAutor;
    }

    private void set_nombreAutor(String _nombreAutor) {
        this._nombreAutor = _nombreAutor;
    }

    public String get_urlImgArticulo() {
        return _urlImgArticulo;
    }

    private void set_urlImgArticulo(String _urlImgArticulo) {
        this._urlImgArticulo = _urlImgArticulo;
    }

    public String get_textoCompleto() {
        return _textoCompleto;
    }

    private void set_textoCompleto(String _textoCompleto) {
        this._textoCompleto = _textoCompleto;
    }

    public String get_hora() {
        return _hora;
    }

    private void set_hora(String _hora) {
        this._hora = _hora;
    }

    public String get_idAutor() {
        return _idUsuario;
    }

    private void set_idAutor(String _idAutor) {
        this._idUsuario = _idAutor;
    }

    public String get_urlArticulo() {
        return _urlArticulo;
    }

    private void set_urlArticulo(String _urlArticulo) {
        this._urlArticulo = _urlArticulo;
    }

    public String get_urlPdf() {
        return _urlPdf;
    }

    private void set_urlPdf(String _urlPdf) {
        this._urlPdf = _urlPdf;
    }

    public String get_urlImgUsuario() {
        return _urlImgUsuario;
    }

    private void set_urlImgUsuario(String _urlImgUsuario) {
        this._urlImgUsuario = _urlImgUsuario;
    }

    public String get_usuario() {
        return _usuario;
    }

    private void set_usuario(String _usuario) {
        this._usuario = _usuario;
    }

    public String get_fuente() {
        return _fuente;
    }

    private void set_fuente(String _fuente) {
        this._fuente = _fuente;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_idArticulo);
        parcel.writeString(_titulo);
        parcel.writeString(_descripcion);
        parcel.writeString(_textoCompleto);
        parcel.writeString(_seccion);
        parcel.writeString(_tipoArticulo);
        parcel.writeString(_fuente);
        parcel.writeString(_fecha);
        parcel.writeString(_hora);
        parcel.writeString(_nombreAutor);
        parcel.writeString(_usuario);
        parcel.writeString(_idUsuario);
        parcel.writeString(_urlImgArticulo);
        parcel.writeString(_urlArticulo);
        parcel.writeString(_urlPdf);
        parcel.writeString(_urlImgUsuario);
    }

    protected DVArticulo(Parcel in) {
        _idArticulo = in.readString();
        _titulo = in.readString();
        _descripcion = in.readString();
        _textoCompleto = in.readString();
        _seccion = in.readString();
        _tipoArticulo = in.readString();
        _fuente = in.readString();
        _fecha = in.readString();
        _hora = in.readString();
        _nombreAutor = in.readString();
        _usuario = in.readString();
        _idUsuario = in.readString();
        _urlImgArticulo = in.readString();
        _urlArticulo = in.readString();
        _urlPdf = in.readString();
        _urlImgUsuario = in.readString();
    }

    public static final Creator<DVArticulo> CREATOR = new Creator<DVArticulo>() {
        @Override
        public DVArticulo createFromParcel(Parcel in) {
            return new DVArticulo(in);
        }

        @Override
        public DVArticulo[] newArray(int size) {
            return new DVArticulo[size];
        }
    };
}
