package com.lajotasoftware.goservice.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.R;

import java.util.List;

public class CustomAdapterPrestadores extends RecyclerView.Adapter {

    List<Usuario> prestadoresList;
    Context context;
    private OnPrestadorListener mOnPrestadorListener;



    public CustomAdapterPrestadores(Context context, List<Usuario> prestadoresList, OnPrestadorListener mOnPrestadorListener) {
        this.prestadoresList = prestadoresList;
        this.context = context;
        this.mOnPrestadorListener = mOnPrestadorListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_prestadores, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mOnPrestadorListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.PrestadorID.setText(prestadoresList.get(position).getId().toString());
        myViewHolder.PrestadorNome.setText(prestadoresList.get(position).getPrimeiroNome());
        myViewHolder.PrestadorBio.setText(prestadoresList.get(position).getBio());
        myViewHolder.position = position;
        myViewHolder.id = prestadoresList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return prestadoresList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Card card = new Card();
        public int position;
        public Long id;
        TextView PrestadorID;
        TextView PrestadorNome;
        TextView PrestadorBio;
        OnPrestadorListener onPrestadorListener;

        public ViewHolder (@NonNull View itemView, OnPrestadorListener onPrestadorListener){
            super(itemView);
            this.onPrestadorListener = onPrestadorListener;
            PrestadorID = itemView.findViewById(R.id.idPrestador);
            PrestadorNome = itemView.findViewById(R.id.ttvNomePrestador);
            PrestadorBio = itemView.findViewById(R.id.ttvBioPrestador);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public interface OnPrestadorListener{
    }
}
