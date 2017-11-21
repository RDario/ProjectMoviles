package com.dariov.moviles.lumieresclub;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dariov.moviles.lumieresclub.adapters.DVPagerAdapterFragments;
import com.dariov.moviles.lumieresclub.fragments.DVFragmentListado;
import com.dariov.moviles.lumieresclub.fragments.DVLoginSingleton;
import com.dariov.moviles.lumieresclub.interfaces.DVListenerMensajePerfil;
import com.dariov.moviles.lumieresclub.utilities.DVHiloDescarga;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Creado por el papu Dario Valdes  on 08/10/2017.
 */

public class DVActivityPerfil extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        DVListenerMensajePerfil, DVHiloDescarga.DVListenerHiloDescarga, View.OnClickListener {
    private DVPagerAdapterFragments _pagerAdapterFragments;
    private LinkedList<DVFragmentListado> _listaFrags;
    private TextView _txtMensajeBorrador;
    private int CAMERA_PIC_REQUEST = 2;
    private File _directoriorRecursos;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private ImageView _imgPerfil;
    private Bitmap _bitmapGlobal;
    private TextView _txtNombre;
    private Uri imgSeleccionada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_activity_mi_perfil);
        _tabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);
        _viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        _txtNombre = (TextView) findViewById(R.id.txtNombre);
        _txtMensajeBorrador = (TextView) findViewById(R.id.txtMensajeBorrador);
        _imgPerfil = (ImageView) findViewById(R.id.imgPerfil);
        _pagerAdapterFragments = new DVPagerAdapterFragments(getSupportFragmentManager());
        _listaFrags = new LinkedList<>();
        _listaFrags.add(DVFragmentListado.newInstanceFromPerfil("1", this));
        _pagerAdapterFragments.setListAdapter(_listaFrags);
        _viewPager.setAdapter(_pagerAdapterFragments);
        _tabLayout.setupWithViewPager(_viewPager);
        _viewPager.setOffscreenPageLimit(_tabLayout.getTabCount());
        _tabLayout.getTabAt(0).setText(getResources().getString(R.string.txt_mensaje_drafts));
        _tabLayout.addOnTabSelectedListener(this);

        if (DVLoginSingleton._urlImgPerfil != null && !DVLoginSingleton._urlImgPerfil.equals("")) {
            Picasso.with(_imgPerfil.getContext()).
                    load(DVLoginSingleton._urlImgPerfil).
                    into(_imgPerfil, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                        }
                    });
        }
        _txtNombre.setText(DVLoginSingleton._nombre);
        _imgPerfil.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Log.e(this.getClass().getSimpleName(), "-----Llego aqui onBackPressed---> ");
        super.onBackPressed();
        finish();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onMostrarMensaje() {
        _txtMensajeBorrador.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHiloDescargaSuccess(String res) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgPerfil) {
            Intent cam = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cam, CAMERA_PIC_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _directoriorRecursos = new File(getFilesDir().getAbsolutePath(), "/resources");
        if (!_directoriorRecursos.exists()) {
            _directoriorRecursos.mkdir();
        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode == RESULT_OK && null != data) {
                imgSeleccionada = data.getData();
                _bitmapGlobal = (Bitmap) data.getExtras().get("data");
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                int _nombre = (int) cal.getTimeInMillis();
                String _nombreimagen = String.valueOf(_nombre);
                if (_nombreimagen.contains("-")) {
                    _nombreimagen = _nombreimagen.replace("-", "");
                }
                String _pathFinal = _directoriorRecursos.getAbsolutePath() + "/" + _nombreimagen;
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgSeleccionada));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        String fotografia = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, (int) (100 * bitmap.getHeight() / bitmap.getWidth()), false);
                        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
                        byteArrayOutputStream.reset();
                        subirFotografia(fotografia);
                    } catch (OutOfMemoryError error) {
                        Log.e("ActivityPerfil", "--OutOfMemoryException----------> " + error);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e("ActivityPerfil", "--FileNotFoundException----------> " + e);
                }
                Log.e("ActivityPerfil", "--nombre.file----------> " + _nombreimagen);
                Log.e("ActivityPerfil", "_directoriorRecursos---> " + _directoriorRecursos);
                Log.e("ActivityPerfil", "----path-final-----> " + _pathFinal);
                _imgPerfil.setImageBitmap(_bitmapGlobal);
                DVLoginSingleton._bitmapImg = _bitmapGlobal;
                DVLoginSingleton._listenerActualizarFoto.actualizarFoto();
            } else {
                Toast.makeText(getBaseContext(), "No se cargo imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void subirFotografia(String fotografia) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("imgFile", fotografia);
        DVHiloDescarga adHiloDescarga = new DVHiloDescarga(new DVHiloDescarga.DVListenerHiloDescarga(){
            @Override
            public void onHiloDescargaSuccess(String res) {
                Log.e("HiloDescargaImagen","------res-------> " + res);
                if (res != null && !res.equals("")) {
                    Picasso.with(getBaseContext()).
                            load(res).
                            into(_imgPerfil, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError() {
                                }
                            });
                }
            }
        }, hashMap);
        adHiloDescarga.execute(URI.create("http://www.elevation.com.mx/pages/pruebas/moviles/upload_image.php"));
    }
}
