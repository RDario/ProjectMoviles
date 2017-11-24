package com.dariov.moviles.lumieresclub.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dariov.moviles.lumieresclub.R;
import com.dariov.moviles.lumieresclub.models.DVArticulo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DarioValdes on 02/10/2017.
 */

public class DVAdapterListado extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Typeface _fuenteLibro, _fuentePoesia, _fuenteCancion, _fuenteCita;
    private LinkedList<DVArticulo> _listaArticulos;
    private View.OnClickListener _clickListener;

    public DVAdapterListado() {
    }

    public void setContext(Context context) {
        _fuenteLibro = Typeface.createFromAsset(context.getAssets(),"fonts/elegant_typewriter.ttf");
        _fuentePoesia = Typeface.createFromAsset(context.getAssets(),"fonts/signature_ancient.ttf");
        _fuenteCancion = Typeface.createFromAsset(context.getAssets(),"fonts/golden_plains_demo.ttf");
        _fuenteCita = Typeface.createFromAsset(context.getAssets(),"fonts/underwood_champion.ttf");
    }

    public void setListaArticulo(LinkedList<DVArticulo> lista) {
        this._listaArticulos = lista;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        this._clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (_listaArticulos.get(position).get_tipoArticulo() != null) {
            switch (_listaArticulos.get(position).get_tipoArticulo()) {
                case "LIBRO":
                    if (_listaArticulos.get(position).get_urlImgArticulo() != null &&
                            !_listaArticulos.get(position).get_urlImgArticulo().equals("") &&
                            !_listaArticulos.get(position).get_urlImgArticulo().equals("null")) {
                        return 1;
                    } else {
                        return 2;
                    }
                case "CANCION":
                    if (_listaArticulos.get(position).get_urlImgArticulo() != null &&
                            !_listaArticulos.get(position).get_urlImgArticulo().equals("") &&
                            !_listaArticulos.get(position).get_urlImgArticulo().equals("null")) {
                        return 1;
                    } else {
                        return 2;
                    }
                case "POEMA":
                    return 3;
                case "CITA TEXTUAL":
                    return 4;
                case "MANUSCRITO":
                    return 5;
                default:
                    return 2;
            }
        } else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_adapter_articulo_full, parent, false);
                return new ViewHolderArticuloFull(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_adapter_articulo_half, parent, false);
                return new ViewHolderArticulo(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_adapter_poesia, parent, false);
                return new ViewHolderPoesia(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_adapter_poesia, parent, false);
                return new ViewHolderPoesia(view);
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_adapter_articulo_half, parent, false);
                return new ViewHolderArticulo(view);
            case 6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_adapter_articulo_half, parent, false);
                return new ViewHolderArticulo(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderArticuloFull) {
            ((ViewHolderArticuloFull) holder)._txtTitulo.setText(_listaArticulos.get(position).get_titulo());
            ((ViewHolderArticuloFull) holder)._txtDescripcion.setText(_listaArticulos.get(position).get_descripcion());
            ((ViewHolderArticuloFull) holder)._txtNomUsuario.setText(_listaArticulos.get(position).get_usuario());
            ((ViewHolderArticuloFull) holder)._txtFecha.setText(_listaArticulos.get(position).get_fecha());
            ((ViewHolderArticuloFull) holder)._txtTitulo.setTypeface(_fuenteLibro);
            ((ViewHolderArticuloFull) holder)._txtDescripcion.setTypeface(_fuenteLibro);
            ((ViewHolderArticuloFull) holder)._imgArticulo.setVisibility(View.GONE);

            if (_listaArticulos.get(position).get_urlImgArticulo() != null && !_listaArticulos.get(position).get_urlImgArticulo().equals("")) {
                Picasso.with(((ViewHolderArticuloFull) holder)._imgArticulo.getContext()).
                        load(_listaArticulos.get(position).get_urlImgArticulo()).
                        into(((ViewHolderArticuloFull) holder)._imgArticulo, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Log.e("Picasso", this.getClass().getSimpleName() + "----------error-al-cargar-imagen---------> ");
                            }
                        });
            } else {
                ((ViewHolderArticuloFull) holder)._imgArticulo.setVisibility(View.GONE);
            }
            if (_listaArticulos.get(position).get_urlImgUsuario() != null && !_listaArticulos.get(position).get_urlImgUsuario().equals("")) {
                Picasso.with(((ViewHolderArticuloFull) holder)._imgUsuario.getContext()).
                        load(_listaArticulos.get(position).get_urlImgUsuario()).
                        into(((ViewHolderArticuloFull) holder)._imgUsuario, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Log.e("Picasso", this.getClass().getSimpleName() + "----------error-al-cargar-imagen---------> ");
                            }
                        });
            } else {
                Picasso.with(((ViewHolderArticuloFull) holder)._imgUsuario.getContext()).
                        load(R.drawable.profile_pic_placeholder).
                        into(((ViewHolderArticuloFull) holder)._imgUsuario, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Log.e("Picasso", this.getClass().getSimpleName() + "----------error-al-cargar-imagen---------> ");
                            }
                        });
            }
            ((ViewHolderArticuloFull) holder)._cardView.setTag(_listaArticulos.get(position));
            ((ViewHolderArticuloFull) holder)._cardView.setOnClickListener(_clickListener);
        } else if (holder instanceof ViewHolderArticulo) {
            ((ViewHolderArticulo) holder)._txtTitulo.setText(_listaArticulos.get(position).get_titulo());
            ((ViewHolderArticulo) holder)._txtDescripcion.setText(_listaArticulos.get(position).get_descripcion());
            ((ViewHolderArticulo) holder)._txtNomUsuario.setText(_listaArticulos.get(position).get_usuario());
            ((ViewHolderArticulo) holder)._txtFecha.setText(_listaArticulos.get(position).get_fecha());
            ((ViewHolderArticulo) holder)._txtTitulo.setTypeface(_fuenteLibro);
            ((ViewHolderArticulo) holder)._txtDescripcion.setTypeface(_fuenteLibro);
            ((ViewHolderArticulo) holder)._txtTitulo.setTag(_listaArticulos.get(position));
            ((ViewHolderArticulo) holder)._txtDescripcion.setTag(_listaArticulos.get(position));
            ((ViewHolderArticulo) holder)._txtTitulo.setOnClickListener(_clickListener);
            ((ViewHolderArticulo) holder)._txtDescripcion.setOnClickListener(_clickListener);

            if (_listaArticulos.get(position).get_urlImgUsuario() != null && !_listaArticulos.get(position).get_urlImgUsuario().equals("")) {
                Picasso.with(((ViewHolderArticulo) holder)._imgUsuario.getContext()).
                        load(_listaArticulos.get(position).get_urlImgUsuario()).
                        into(((ViewHolderArticulo) holder)._imgUsuario, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Log.e("Picasso", this.getClass().getSimpleName() + "----------error-al-cargar-imagen---------> ");
                            }
                        });
            } else {
                Picasso.with(((ViewHolderArticulo) holder)._imgUsuario.getContext()).
                        load(R.drawable.profile_pic_placeholder).
                        into(((ViewHolderArticulo) holder)._imgUsuario, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Log.e("Picasso", this.getClass().getSimpleName() + "----------error-al-cargar-imagen---------> ");
                            }
                        });
            }

        } else if (holder instanceof ViewHolderPoesia) {
            ((ViewHolderPoesia) holder)._txtTitulo.setText(_listaArticulos.get(position).get_nombreAutor());
            ((ViewHolderPoesia) holder)._txtDescripcion.setText("'"+_listaArticulos.get(position).get_descripcion()+"'");
            ((ViewHolderPoesia) holder)._txtNomUsuario.setText(_listaArticulos.get(position).get_usuario());
            ((ViewHolderPoesia) holder)._txtFecha.setText(_listaArticulos.get(position).get_fecha());
            ((ViewHolderPoesia) holder)._txtTitulo.setTypeface(_fuentePoesia);
            ((ViewHolderPoesia) holder)._txtDescripcion.setTypeface(_fuentePoesia);
            ((ViewHolderPoesia) holder)._txtTitulo.setTag(_listaArticulos.get(position));
            ((ViewHolderPoesia) holder)._txtDescripcion.setTag(_listaArticulos.get(position));
            ((ViewHolderPoesia) holder)._txtTitulo.setOnClickListener(_clickListener);
            ((ViewHolderPoesia) holder)._txtDescripcion.setOnClickListener(_clickListener);

            if (_listaArticulos.get(position).get_urlImgUsuario() != null && !_listaArticulos.get(position).get_urlImgUsuario().equals("")) {
                Picasso.with(((ViewHolderPoesia) holder)._imgUsuario.getContext()).
                        load(_listaArticulos.get(position).get_urlImgUsuario()).
                        into(((ViewHolderPoesia) holder)._imgUsuario, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Log.e("Picasso", this.getClass().getSimpleName() + "----------error-al-cargar-imagen---------> ");
                            }
                        });
            } else {
                Picasso.with(((ViewHolderPoesia) holder)._imgUsuario.getContext()).
                        load(R.drawable.profile_pic_placeholder).
                        into(((ViewHolderPoesia) holder)._imgUsuario, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Log.e("Picasso", this.getClass().getSimpleName() + "----------error-al-cargar-imagen---------> ");
                            }
                        });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (_listaArticulos != null) {
            return _listaArticulos.size();
        }
        return 0;
    }

    public static class ViewHolderArticuloFull extends RecyclerView.ViewHolder {
        CardView _cardView;
        TextView _txtTitulo;
        TextView _txtDescripcion;
        TextView _txtNomUsuario;
        TextView _txtFecha;
        ImageView _imgArticulo;
        CircleImageView _imgUsuario;

        ViewHolderArticuloFull(View itemView) {
            super(itemView);
            _cardView = itemView.findViewById(R.id.cardView);
            _txtTitulo = itemView.findViewById(R.id.txtTitulo);
            _txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            _txtNomUsuario = itemView.findViewById(R.id.txtNomUsuario);
            _txtFecha = itemView.findViewById(R.id.txtFecha);
            _imgArticulo = itemView.findViewById(R.id.imgArticulo);
            _imgUsuario = itemView.findViewById(R.id.imgUsuario);
        }
    }

    public static class ViewHolderArticulo extends RecyclerView.ViewHolder {
        TextView _txtTitulo;
        TextView _txtDescripcion;
        TextView _txtNomUsuario;
        TextView _txtFecha;
        CircleImageView _imgUsuario;

        ViewHolderArticulo(View itemView) {
            super(itemView);
            _txtTitulo = itemView.findViewById(R.id.txtTitulo);
            _txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            _txtNomUsuario = itemView.findViewById(R.id.txtNomUsuario);
            _txtFecha = itemView.findViewById(R.id.txtFecha);
            _imgUsuario = itemView.findViewById(R.id.imgUsuario);
        }
    }

    public static class ViewHolderPoesia extends RecyclerView.ViewHolder {
        TextView _txtTitulo;
        TextView _txtDescripcion;
        TextView _txtNomUsuario;
        TextView _txtFecha;
        CircleImageView _imgUsuario;

        ViewHolderPoesia(View itemView) {
            super(itemView);
            _txtTitulo = itemView.findViewById(R.id.txtTitulo);
            _txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            _txtNomUsuario = itemView.findViewById(R.id.txtNomUsuario);
            _txtFecha = itemView.findViewById(R.id.txtFecha);
            _imgUsuario = itemView.findViewById(R.id.imgUsuario);
        }
    }
}
