package com.dariov.moviles.lumieresclub.fragments;

import android.graphics.Bitmap;

import com.dariov.moviles.lumieresclub.interfaces.DVListenerActualizarFoto;

/**
 * Creado por el papu Dario Valdes  on 20/11/2017.
 */

public class DVLoginSingleton {
    public static String _id;
    public static String _nombre;
    public static String _correo;
    public static String _contrasenia;
    public static String _urlImgPerfil;
    public static Bitmap _bitmapImg;
    public static DVListenerActualizarFoto _listenerActualizarFoto;

    public static void set_nombre(String _nombre) {
        DVLoginSingleton._nombre = _nombre;
    }

    public static void set_correo(String _correo) {
        DVLoginSingleton._correo = _correo;
    }

    public static void set_contrasenia(String _contrasenia) {
        DVLoginSingleton._contrasenia = _contrasenia;
    }

    public static void set_urlImgPerfil(String _urlImgPerfil) {
        DVLoginSingleton._urlImgPerfil = _urlImgPerfil;
    }

    public static void setDatos(String nom, String email, String pass, String img) {
        set_nombre(nom);
        set_correo(email);
        set_contrasenia(pass);
        set_urlImgPerfil(img);
    }
}
