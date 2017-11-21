package com.dariov.moviles.lumieresclub.utilities;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Creado por el papu Dario Valdes  on 20/11/2017.
 */

public class ADSaveImageAsynTask extends AsyncTask<Bitmap, Void, Void> {
    private ImageSavedListener _savedListener;
    private String _directorio;
    private String _imageUrl;

    public ADSaveImageAsynTask(String imageUri, String directorio, ImageSavedListener listener) {
        _imageUrl = imageUri;
        _directorio = directorio;
        _savedListener = listener;
    }

    @Override
    protected Void doInBackground(Bitmap... arg0) {
        File mypath = new File(_directorio, String.valueOf(_imageUrl.hashCode()));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            arg0[0].compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void params) {
        if(_savedListener != null) {
            _savedListener.imageSaved();
        }
    }

    public interface ImageSavedListener {
        void imageSaved();
    }
}
