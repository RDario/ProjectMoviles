package com.dariov.moviles.lumieresclub.models;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DarioValdes on 02/10/2017.
 */

public class DVArticulo {
    private String _idArticulo;
    private String _titulo;
    private String _descripcion;
    private String _seccion;
    private String _tipoArticulo;
    private String _fecha;
    private String _nombreUsuario;
    private String _urlImgArticulo;
    private String _urlUsuario;

    public DVArticulo() {}

    public DVArticulo(JSONArray jsonArray) {
    }

    public DVArticulo(JSONObject jsonArray) {
    }

    //Constructor de prueba
    public DVArticulo(String titulo, String decripcion, String fecha, String nomUser) {
        set_titulo(titulo);
        set_descripcion(decripcion);
        set_fecha(fecha);
        set_nombreUsuario(nomUser);
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

    public String get_nombreUsuario() {
        return _nombreUsuario;
    }

    private void set_nombreUsuario(String _nombreUsuario) {
        this._nombreUsuario = _nombreUsuario;
    }

    public String get_urlImgArticulo() {
        return _urlImgArticulo;
    }

    private void set_urlImgArticulo(String _urlImgArticulo) {
        this._urlImgArticulo = _urlImgArticulo;
    }

    public String get_urlUsuario() {
        return _urlUsuario;
    }

    private void set_urlUsuario(String _urlUsuario) {
        this._urlUsuario = _urlUsuario;
    }
}
