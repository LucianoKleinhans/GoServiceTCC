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

import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.R;

import java.util.List;

public class CustomAdapterProposta extends RecyclerView.Adapter {

    List<Proposta> propostasLists;
    Context context;
    private final OnPropostaListener mOnPropostaListener;

    public CustomAdapterProposta(Context context, List<Proposta> propostasLists, OnPropostaListener OnPropostaListener) {
        this.propostasLists = propostasLists;
        this.context = context;
        this.mOnPropostaListener = OnPropostaListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_propostas, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mOnPropostaListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;

        myViewHolder.PropostaID.setText(propostasLists.get(position).getId().toString());
        myViewHolder.PropostaPrestador.setText(propostasLists.get(position).getId_Prestador().getPrimeiroNome());
        myViewHolder.PropostaDesc.setText(propostasLists.get(position).getObservacao());
        myViewHolder.PropostaValor.setText("Valor proposto: R$"+propostasLists.get(position).getValor().toString());
        myViewHolder.position = position;
        myViewHolder.id = propostasLists.get(position).getId();
        myViewHolder.idCliente = propostasLists.get(position).getId_Cliente().getId();
        myViewHolder.idPrestador = propostasLists.get(position).getId_Prestador().getId();
    }

    @Override
    public int getItemCount() {
        return propostasLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Proposta proposta = new Proposta();
        public int position;
        public Long id, idCliente, idPrestador;
        TextView PropostaID;
        TextView PropostaPrestador;
        TextView PropostaDesc;
        TextView PropostaValor;
        OnPropostaListener onPropostaListener;

        public ViewHolder (@NonNull View itemView, OnPropostaListener onPropostaListener){
            super(itemView);
            this.onPropostaListener = onPropostaListener;
            PropostaID = itemView.findViewById(R.id.idProposta);
            PropostaPrestador = itemView.findViewById(R.id.ttvPrestadorProposta);
            PropostaDesc = itemView.findViewById(R.id.ttvDescProposta);
            PropostaValor = itemView.findViewById(R.id.ttvValorProposta);
            itemView.findViewById(R.id.btnAceitarProposta).setOnClickListener(this);
            itemView.findViewById(R.id.btnNegociarProposta).setOnClickListener(this);
            itemView.findViewById(R.id.btnRecusarProposta).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String text = b.getText().toString();
            switch (text) {
                case "Aceitar":
                    onPropostaListener.onPropostaAceitarClick(getAdapterPosition(), id, idCliente, idPrestador);
                    break;
                case "Negociar":
                    onPropostaListener.onPropostaNegociarClick(getAdapterPosition(), id, idCliente, idPrestador);
                    break;
                case "Recusar":
                    onPropostaListener.onPropostaRecusarClick(getAdapterPosition(), id, idCliente, idPrestador);
                    break;
            }
        }
    }

    public interface OnPropostaListener{
        void onPropostaNegociarClick(int adapterPosition, Long id, Long idCliente, Long idPrestador);
        void onPropostaAceitarClick(int adapterPosition, Long id, Long idCliente, Long idPrestador);
        void onPropostaRecusarClick(int adapterPosition, Long id, Long idCliente, Long idPrestador);
    }
}
