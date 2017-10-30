package com.dariov.moviles.lumieresclub.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Creado por el papu Dario Valdes  on 26/08/2017.
 */

public class DVHiloDescarga extends AsyncTask<URI, Integer, String> {
    private DVListenerHiloDescarga _listenerHiloDescarga;
    private URI _uri;

    public DVHiloDescarga(DVListenerHiloDescarga listener) {
        _listenerHiloDescarga = listener;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(URI... arg0) {
        _uri = arg0[0];
        String json = null;
        if (_uri != null && _uri.isAbsolute()) {
            Log.e(this.getClass().getSimpleName(), this.getClass().getSimpleName() + "--------uri-descarga-------->> " + _uri);
            json = descargaGet(_uri);
        }
        return json;
    }

    public String descargaGet(URI url) {
        String response = null;
        if (url != null) {
            int status = 0;
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.toURL().openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Accept-Charset", "application/x-www-form-urlencoded; charset=utf-8");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                status = httpURLConnection.getResponseCode();
                Log.e(this.getClass().getSimpleName(), this.getClass().getSimpleName() + "--------status-------->> " + status);
                if (status == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder stringBuilder = new StringBuilder();
                    String linea;
                    while ((linea = bufferedReader.readLine()) != null) {
                        stringBuilder.append(linea).append("\r\n");
                    }
                    bufferedReader.close();
                    response = stringBuilder.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getSimpleName(), "---------Exception------>>> " + e + " --status--> " + status);
            } finally {
                assert httpURLConnection != null;
                httpURLConnection.disconnect();
            }
        }
        return response;
    }

    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        _listenerHiloDescarga.onHiloDescargaSuccess(res);
    }

    public interface DVListenerHiloDescarga {
        void onHiloDescargaSuccess(String res);
    }
}
