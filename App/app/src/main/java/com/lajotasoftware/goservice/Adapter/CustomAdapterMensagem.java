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

import com.lajotasoftware.goservice.Entity.Mensagem;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.R;

import java.util.List;

public class CustomAdapterMensagem extends RecyclerView.Adapter {

    List<Mensagem> mensagemLists;
    Context context;

    public CustomAdapterMensagem(Context context, List<Mensagem> mensagemLists) {
        this.mensagemLists = mensagemLists;
        this.context = context;
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
        myViewHolder.MensagemText.setText(mensagemLists.get(position).getMensagem().toString());
        //myViewHolder.MensagemValor.setText("Valor Proposto: R$"+mensagemLists.get(position).toString());
        myViewHolder.position = position;
        myViewHolder.id = mensagemLists.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mensagemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private Mensagem mensagem = new Mensagem();
        public int position;
        public Long id;
        TextView MensagemText;
        //TextView MensagemValor;

        public ViewHolder (@NonNull View itemView){
            super(itemView);
            MensagemText = itemView.findViewById(R.id.ttvMensagemProposta);
            //MensagemValor = itemView.findViewById(R.id.ttvValorMensagemProposta);
        }
    }
}
