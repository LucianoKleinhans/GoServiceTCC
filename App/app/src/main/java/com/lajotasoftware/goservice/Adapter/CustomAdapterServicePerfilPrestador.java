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
import com.lajotasoftware.goservice.Frames.Servicos;
import com.lajotasoftware.goservice.R;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapterServicePerfilPrestador extends RecyclerView.Adapter {

    List<Servico> servicosLists;
    Context context;
    private final OnServicoListener mOnServicoListener;



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
        myViewHolder.ServicoPrestador.setText(servicosLists.get(position).getId_Prestador().getId().toString());
        myViewHolder.ServicoNome.setText(servicosLists.get(position).getNome());
        myViewHolder.ServicoDesc.setText(servicosLists.get(position).getObsServico());
        myViewHolder.ServicoValor.setText("Valor: R$"+servicosLists.get(position).getValor().toString());
        myViewHolder.position = position;
        myViewHolder.id = servicosLists.get(position).getId();
        myViewHolder.idPrestador = servicosLists.get(position).getId_Prestador().getId();
    }

    @Override
    public int getItemCount() {
        return servicosLists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Card card = new Card();
        public int position;
        public Long id, idPrestador;
        TextView ServicoId;
        TextView ServicoPrestador;
        TextView ServicoNome;
        TextView ServicoDesc;
        TextView ServicoValor;
        OnServicoListener onServicoListener;

        public ViewHolder (@NonNull View itemView, OnServicoListener onServicoListener){
            super(itemView);
            this.onServicoListener = onServicoListener;
            ServicoId = itemView.findViewById(R.id.idServicoPrestador);
            ServicoPrestador = itemView.findViewById(R.id.idPrestador);
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
                onServicoListener.SolicitaServico(getAdapterPosition(), id, idPrestador);
            }
        }
    }

    public interface OnServicoListener{
        void SolicitaServico (int position, Long id, Long idPrestador);
    }
}
