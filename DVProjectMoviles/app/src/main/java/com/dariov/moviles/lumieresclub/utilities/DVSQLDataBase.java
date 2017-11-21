package com.dariov.moviles.lumieresclub.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Creado por el papu Dario Valdes  on 05/11/2017.
 */

/*
* ID
* TITULO
* TEXTO
* AUTOR
* FUENTE
* TIPO_FUENTE
* IS_PUBLICO
* USUARIO
* USUARIO_ID
* */

public class DVSQLDataBase extends SQLiteOpenHelper {
    public SQLiteDatabase _mydb;
    private Context _context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "databaseMoviles.db";
    public static final String REQUEST_CODE = "requestCode";
    public static final String TABLE_ARTICULO = "articulo";
    public static final String COLUMN_ID_ARTICULO = "idArticulo";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_TEXTO = "texto";
    public static final String COLUMN_SECCION = "seccion";
    public static final String COLUMN_TIPO_FUENTE = "tipoArticulo";
    public static final String COLUMN_FUENTE = "fuente";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "hora";
    public static final String COLUMN_AUTOR = "autor";
    public static final String COLUMN_ID_USUARIO = "idUsuario";
    public static final String COLUMN_NOMBRE_USUARIO = "nombreUsuario";
    public static final String COLUMN_URL_IMG_USUARIO = "urlImgUsuario";
    public static final String COLUMN_URL_IMG_ARTICULO = "urlImgArticulo";

    public static final String TABLE_AUTORES = "autores";
    public static final String COLUMN_AUTOR_NOMBRE = "nomAutor";

    public static final String TABLE_FUENTES = "fuentes";
    public static final String COLUMN_FUENTES_NOMBRE = "nomFuentes";

    private static final String TABLE_ARTICULO_CREATE = "create table "
            + TABLE_ARTICULO
            + "(" + COLUMN_ID_ARTICULO + " integer primary key autoincrement, "
            + COLUMN_TITULO + " text, "
            + COLUMN_DESCRIPCION + " text, "
            + COLUMN_TEXTO + " text, "
            + COLUMN_SECCION + " text, "
            + COLUMN_TIPO_FUENTE + " text, "
            + COLUMN_FUENTE + " text, "
            + COLUMN_FECHA + " text, "
            + COLUMN_HORA + " text, "
            + COLUMN_AUTOR + " text, "
            + COLUMN_NOMBRE_USUARIO + " text, "
            + COLUMN_ID_USUARIO + " text, "
            + COLUMN_URL_IMG_USUARIO + " text, "
            + COLUMN_URL_IMG_ARTICULO + " text);";

    private static final String TABLE_AUTORES_CREATE = "create table "
            + TABLE_AUTORES
            + "(" + COLUMN_AUTOR_NOMBRE + " text);";

    private static final String TABLE_FUENTES_CREATE = "create table "
            + TABLE_FUENTES
            + "(" + COLUMN_FUENTES_NOMBRE + " text);";

    public DVSQLDataBase(Context context, String s, Object o, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ARTICULO_CREATE);
        db.execSQL(TABLE_AUTORES_CREATE);
        db.execSQL(TABLE_FUENTES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICULO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUENTES);
        onCreate(db);
    }

    public void deleteRow(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ARTICULO + " WHERE idArticulo" + "='"+value+"'");
        db.close();
    }

    public SQLiteDatabase getDB() {
        return this.getReadableDatabase();
    }
}
