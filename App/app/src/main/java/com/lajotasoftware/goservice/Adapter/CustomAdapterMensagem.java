package com.lajotasoftware.goservice.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Mensagem;
import com.lajotasoftware.goservice.R;

import java.util.List;
import java.util.Objects;

public class CustomAdapterMensagem extends RecyclerView.Adapter {

    private final Long idUser;
    List<Mensagem> mensagemLists;
    Context context;

    public CustomAdapterMensagem(Context context, List<Mensagem> mensagemLists, Long idUsuario) {
        this.mensagemLists = mensagemLists;
        this.context = context;
        this.idUser = idUsuario;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_message, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.position = position;
        myViewHolder.id = mensagemLists.get(position).getId();
        myViewHolder.enviadoPor = mensagemLists.get(position).getSendBy().getId();
        if (Objects.equals(idUser, myViewHolder.enviadoPor)){
            myViewHolder.mensagemText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }else {
            myViewHolder.mensagemText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
        myViewHolder.mensagemText.setText(mensagemLists.get(position).getMensagem().toString());
    }

    @Override
    public int getItemCount() {
        return mensagemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Long idCliente;
        public Long idPrestador;
        public Long enviadoPor;
        private Mensagem mensagem = new Mensagem();
        public int position;
        public Long id;

        MaterialTextView mensagemText;
        ConstraintLayout mensagemList;
        //TextView MensagemValor;

        public ViewHolder (@NonNull View itemView){
            super(itemView);
            mensagemText = itemView.findViewById(R.id.ttvMensagemProposta);
            mensagemList = itemView.findViewById(R.id.mensagemListBack);
            //MensagemValor = itemView.findViewById(R.id.ttvValorMensagemProposta);
        }
    }
}
