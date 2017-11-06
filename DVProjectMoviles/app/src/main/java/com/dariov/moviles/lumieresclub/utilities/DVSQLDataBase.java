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
    public static final String COLUMN_TEXTO = "texto";
    public static final String COLUMN_AUTOR = "autor";
    public static final String COLUMN_FUENTE = "fuente";
    public static final String COLUMN_TIPO_FUENTE = "tipoFuente";
    public static final String COLUMN_NOMBRE_USUARIO = "nombreUsuario";
    public static final String COLUMN_IS_PUBLICA = "isPublica";
    public static final String COLUMN_ID_USUARIO = "idUsuario";

    private static final String TABLE_ARTICULO_CREATE = "create table "
            + TABLE_ARTICULO
            + "(" + COLUMN_ID_ARTICULO + " integer primary key autoincrement, "
            + REQUEST_CODE + " integer not null, "
            + COLUMN_TITULO + " text not null, "
            + COLUMN_TEXTO + " text not null, "
            + COLUMN_AUTOR + " text not null, "
            + COLUMN_FUENTE + " text not null, "
            + COLUMN_TIPO_FUENTE + " text not null, "
            + COLUMN_IS_PUBLICA + " text not null, "
            + COLUMN_NOMBRE_USUARIO + " text not null, "
            + COLUMN_ID_USUARIO + " int not null);";

    public DVSQLDataBase(Context context, String s, Object o, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ARTICULO_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICULO);
        onCreate(db);
    }

    public SQLiteDatabase getDB() {
        return this.getReadableDatabase();
    }
}
