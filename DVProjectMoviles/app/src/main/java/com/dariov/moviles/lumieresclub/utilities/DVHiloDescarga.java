package com.dariov.moviles.lumieresclub.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Creado por el papu Dario Valdes  on 26/08/2017.
 */

public class DVHiloDescarga extends AsyncTask<URI, Integer, String> {
    protected HashMap<String, String> _mapParameter = new HashMap<>();
    private DVListenerHiloDescarga _listenerHiloDescarga;
    private URI _uri;

    public DVHiloDescarga(DVListenerHiloDescarga listener) {
        _listenerHiloDescarga = listener;
    }

    public DVHiloDescarga(DVListenerHiloDescarga listenerConnection, HashMap<String, String> mapParameter){
        _listenerHiloDescarga = listenerConnection;
        _mapParameter = mapParameter;
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
            if (_mapParameter == null || _mapParameter.equals("{}") || _mapParameter.size() == 0) {
                json = descargaGet(_uri);
            } else {
                json = descargaPost(_uri);
            }
        }
        return json;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            if (entry.getValue() != null){
                if ( entry.getValue().contains("+")){
                    entry.setValue(entry.getValue().replace("+", "%2B"));
                }
            }
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return result.toString();
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

    public String descargaPost(URI url) {
        Log.e("ADHiloDescarga","------url-Post------->> "+url);
        String response = null;
        if (url != null){
            int status = 0;
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.toURL().openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Accept-Charset", "application/x-www-form-urlencoded; charset=utf-8");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.addRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(_mapParameter));
                writer.flush();
                writer.close();
                os.close();

                status = httpURLConnection.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder stringBuilder = new StringBuilder();
                    String linea = null;
                    while ((linea = bufferedReader.readLine()) != null){
                        stringBuilder.append(linea).append("\n");
                    }
                    response = stringBuilder.toString();
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ADHiloDescarga", "------IO-Exception------->> " + e);
                assert httpURLConnection != null;
                Log.e("ADHiloDescarga", "------headers------->> " + httpURLConnection.getHeaderFields());
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
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
