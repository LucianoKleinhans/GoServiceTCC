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
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.R;

import java.util.List;

public class CustomAdapterServicePerfilPrestador extends RecyclerView.Adapter {

    List<Servico> servicosLists;
    Context context;
    private OnServicoListener mOnServicoListener;



    public CustomAdapterServicePerfilPrestador(Context context, List<Servico> servicosLists, OnServicoListener mOnServicoListener) {
        this.servicosLists = servicosLists;
        this.context = context;
        this.mOnServicoListener = mOnServicoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_serviceprestador, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mOnServicoListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.ServicoId.setText(servicosLists.get(position).getId().toString());
        myViewHolder.ServicoNome.setText(servicosLists.get(position).getNome().toString());
        myViewHolder.ServicoDesc.setText(servicosLists.get(position).getObsServico().toString());
        myViewHolder.ServicoValor.setText("Valor: R$"+servicosLists.get(position).getValor().toString());
        myViewHolder.position = position;
        myViewHolder.id = servicosLists.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return servicosLists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Card card = new Card();
        public int position;
        public Long id;
        TextView ServicoId;
        TextView ServicoNome;
        TextView ServicoDesc;
        TextView ServicoValor;
        OnServicoListener onServicoListener;

        public ViewHolder (@NonNull View itemView, OnServicoListener onServicoListener){
            super(itemView);
            this.onServicoListener = onServicoListener;
            ServicoId = itemView.findViewById(R.id.idServicoPrestador);
            ServicoNome = itemView.findViewById(R.id.ttvNomeServicoPrestador);
            ServicoDesc = itemView.findViewById(R.id.ttvDescServicoPrestador);
            ServicoValor = itemView.findViewById(R.id.ttvValorServicoPrestador);
            itemView.findViewById(R.id.btnSolicitaServico).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String text = b.getText().toString();
            if ("Solicitar".equals(text)) {
                onServicoListener.SolicitaServico(getAdapterPosition(), id);
            }
        }
    }

    public interface OnServicoListener{
        void SolicitaServico (int position, Long id);
    }
}