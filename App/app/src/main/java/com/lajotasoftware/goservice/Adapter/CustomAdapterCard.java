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

import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.R;

import java.util.List;

public class CustomAdapterCard extends RecyclerView.Adapter {

    private final String parametro;
    List<SolicitaServico> cardLists;
    Context context;
    private final OnCardListener mOnCardListener;



    public CustomAdapterCard(Context context, List<SolicitaServico> cardLists, OnCardListener onCardListener, String parametro) {
        this.cardLists = cardLists;
        this.context = context;
        this.mOnCardListener = onCardListener;
        this.parametro = parametro;
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
        myViewHolder.CardServicoNome.setText(cardLists.get(position).getNomeServico());
        myViewHolder.CardServicoDesc.setText(cardLists.get(position).getDescricaoSolicitacao());
        myViewHolder.CardServicoValor.setText("Valor: R$"+cardLists.get(position).getValor().toString());
        if (cardLists.get(position).getValorProposto() == 0){
            myViewHolder.CardServicoValorAtual.setText("Sem Propostas!");
        } else {
            myViewHolder.CardServicoValorAtual.setText("Valor atual: R$"+cardLists.get(position).getValorProposto().toString());
        }
        myViewHolder.position = position;
        myViewHolder.id = cardLists.get(position).getId();
        myViewHolder.idCliente = cardLists.get(position).getId_Cliente().getId();
    }

    @Override
    public int getItemCount() {
        return cardLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Card card = new Card();
        public int position;
        public Long id, idCliente;
        TextView CardServicoId;
        TextView CardServicoNome;
        TextView CardServicoDesc;
        TextView CardServicoValor;
        TextView CardServicoValorAtual;
        OnCardListener onCardListener;

        public ViewHolder (@NonNull View itemView, OnCardListener onCardListener){
            super(itemView);
            this.onCardListener = onCardListener;
            CardServicoId = itemView.findViewById(R.id.idServico);
            CardServicoNome = itemView.findViewById(R.id.ttvNomeServico);
            CardServicoDesc = itemView.findViewById(R.id.ttvDescServico);
            CardServicoValor = itemView.findViewById(R.id.ttvValorServico);
            CardServicoValorAtual = itemView.findViewById(R.id.ttvCardValorAtual);
            itemView.findViewById(R.id.btnVisualizarCardServico).setOnClickListener(this);
            itemView.findViewById(R.id.btnRemoverServico).setOnClickListener(this);
            itemView.findViewById(R.id.btnEditarServico).setOnClickListener(this);
            itemView.findViewById(R.id.btnFazerProposta).setOnClickListener(this);
            itemView.findViewById(R.id.btnVizualizarProposta).setOnClickListener(this);
            if (parametro.equals("MEUS_CARDS")){
                itemView.findViewById(R.id.btnVisualizarCardServico).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnRemoverServico).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnEditarServico).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnFazerProposta).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnVizualizarProposta).setVisibility(View.INVISIBLE);
            }else if (parametro.equals("CARDS_PUBLICOS")) {
                itemView.findViewById(R.id.btnVisualizarCardServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnRemoverServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnEditarServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnFazerProposta).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnVizualizarProposta).setVisibility(View.INVISIBLE);
            }else if (parametro.equals("CARDS_PROPOSTAS_ENVIADAS")) {
                itemView.findViewById(R.id.btnVisualizarCardServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnRemoverServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnEditarServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnFazerProposta).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnVizualizarProposta).setVisibility(View.VISIBLE);
            }else if (parametro.equals("CARDS_PROPOSTAS_FINALIZADAS")) {
                itemView.findViewById(R.id.btnVisualizarCardServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnRemoverServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnEditarServico).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnFazerProposta).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnVizualizarProposta).setVisibility(View.INVISIBLE);
            }
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
                case "Fazer Proposta":
                    onCardListener.onCardFazerPropostaClick(getAdapterPosition(), id, idCliente);
                    break;
                case "Vizualizar Proposta":
                    onCardListener.onCardVizualizarPropostaClick(getAdapterPosition(),id, idCliente);
            }
        }
    }

    public interface OnCardListener{
        void onCardVisualizarClick (int position, Long id);
        void onCardRemoverClick (int position, Long id);
        void onCardEditarClick (int position, Long id);
        void onCardFazerPropostaClick(int position, Long id, Long idCliente);
        void onCardVizualizarPropostaClick(int position, Long id, Long idCliente);
    }
}
