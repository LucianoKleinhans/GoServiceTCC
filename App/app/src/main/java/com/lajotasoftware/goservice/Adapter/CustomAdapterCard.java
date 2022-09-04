package com.lajotasoftware.goservice.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.Functions.Function;
import com.lajotasoftware.goservice.R;

import java.util.List;

import retrofit2.Callback;

public class CustomAdapterCard extends RecyclerView.Adapter {

    List<SolicitaServico> cardLists;
    Context context;
    private OnCardListener mOnCardListener;



    public CustomAdapterCard(Context context, List<SolicitaServico> cardLists, OnCardListener onCardListener) {
        this.cardLists = cardLists;
        this.context = context;
        this.mOnCardListener = onCardListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_cardservice, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mOnCardListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.CardServicoId.setText(cardLists.get(position).getId().toString());
        myViewHolder.CardServicoNome.setText(cardLists.get(position).getNomeServico().toString());
        myViewHolder.CardServicoDesc.setText(cardLists.get(position).getDescricaoSolicitacao().toString());
        myViewHolder.CardServicoValor.setText(cardLists.get(position).getValor().toString());
        myViewHolder.position = position;
        myViewHolder.id = cardLists.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return cardLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Card card = new Card();
        public int position;
        public Long id;
        TextView CardServicoId;
        TextView CardServicoNome;
        TextView CardServicoDesc;
        TextView CardServicoValor;
        OnCardListener onCardListener;

        public ViewHolder (@NonNull View itemView, OnCardListener onCardListener){
            super(itemView);
            this.onCardListener = onCardListener;
            CardServicoId = itemView.findViewById(R.id.idCardServico);
            CardServicoNome = itemView.findViewById(R.id.ttvCardNomeServico);
            CardServicoDesc = itemView.findViewById(R.id.textView5);
            CardServicoValor = itemView.findViewById(R.id.ttvCardValorInicial);
            itemView.findViewById(R.id.btnVisualizarCardServico).setOnClickListener(this);
            itemView.findViewById(R.id.btnRemoverCardServico).setOnClickListener(this);
            itemView.findViewById(R.id.btnEditarCardServico2).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String text = b.getText().toString();
            switch (text) {
                case "Visualizar":
                    onCardListener.onCardVisualizarClick(getAdapterPosition(), id);
                    break;
                case "Remover":
                    onCardListener.onCardRemoverClick(getAdapterPosition(), id);
                    break;
                case "Editar":
                    onCardListener.onCardEditarClick(getAdapterPosition(), id);
                    break;
            }
        }
    }

    public interface OnCardListener{
        void onCardVisualizarClick (int position, Long id);
        void onCardRemoverClick (int position, Long id);
        void onCardEditarClick (int position, Long id);
    }
}