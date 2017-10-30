package com.dariov.moviles.lumieresclub.adapters;

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
    private LinkedList<DVArticulo> _listaArticulos;
    private View.OnClickListener _clickListener;

    public DVAdapterListado() {
    }

    public void setListaArticulo(LinkedList<DVArticulo> lista) {
        this._listaArticulos = lista;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        this._clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_adapter_articulo, parent, false);
        return new ViewHolderArticuloFull(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderArticuloFull) {
            ((ViewHolderArticuloFull) holder)._txtTitulo.setText(_listaArticulos.get(position).get_titulo());
            ((ViewHolderArticuloFull) holder)._txtDescripcion.setText(_listaArticulos.get(position).get_descripcion());
            ((ViewHolderArticuloFull) holder)._txtNomUsuario.setText(_listaArticulos.get(position).get_nombreAutor());
            ((ViewHolderArticuloFull) holder)._txtFecha.setText(_listaArticulos.get(position).get_fecha());

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
            ((ViewHolderArticuloFull) holder)._cardView.setOnClickListener(_clickListener);
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

    public static class ViewHolderCitaTextual extends RecyclerView.ViewHolder {
        TextView _txtTitulo;
        TextView _txtDescripcion;
        TextView _txtNomUsuario;
        TextView _txtFecha;
        CircleImageView _imgUsuario;

        ViewHolderCitaTextual(View itemView) {
            super(itemView);
            _txtTitulo = itemView.findViewById(R.id.txtTitulo);
            _txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            _txtNomUsuario = itemView.findViewById(R.id.txtNomUsuario);
            _txtFecha = itemView.findViewById(R.id.txtFecha);
            _imgUsuario = itemView.findViewById(R.id.imgUsuario);
        }
    }
}
