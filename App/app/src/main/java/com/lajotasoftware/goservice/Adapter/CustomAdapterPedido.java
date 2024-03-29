package com.lajotasoftware.goservice.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.R;

import java.text.NumberFormat;
import java.util.List;

public class CustomAdapterPedido extends RecyclerView.Adapter {

    List<Pedido> pedidosList;
    Context context;
    private final OnPedidoListener mOnPedidoListener;
    String parametro;
    Long idUser;

    public CustomAdapterPedido(Context context, List<Pedido> pedidosList, OnPedidoListener mOnPedidoListener, String parametro, Long idUsuario) {
        this.pedidosList = pedidosList;
        this.context = context;
        this.mOnPedidoListener = mOnPedidoListener;
        this.parametro = parametro;
        this.idUser = idUsuario;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_pedido, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnPedidoListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        String nome = "UserUknown's";

        if (parametro.equals("ENVIADAS")) {
            nome = pedidosList.get(position).getId_Prestador().getPrimeiroNome();
        }else if (parametro.equals("RECEBIDAS")) {
            nome = pedidosList.get(position).getId_Cliente().getPrimeiroNome();
        }else if (parametro.equals("PROGRESSO")) {
            if (pedidosList.get(position).getId_Cliente().getId() == idUser){
                nome = pedidosList.get(position).getId_Prestador().getPrimeiroNome();
            }else{
                nome = pedidosList.get(position).getId_Cliente().getPrimeiroNome();
            }
        }else if (parametro.equals("FINALIZADO")) {
            if (pedidosList.get(position).getId_Cliente().getId() == idUser){
                nome = pedidosList.get(position).getId_Prestador().getPrimeiroNome();
            }else {
                nome = pedidosList.get(position).getId_Cliente().getPrimeiroNome();
            }
        }

        if(pedidosList.get(position).getServicoSolicitado()){
            myViewHolder.idPedido.setText(pedidosList.get(position).getId().toString());
            myViewHolder.clientePedido.setText(nome);
            myViewHolder.servicoPedido.setText(pedidosList.get(position).getId_Proposta().getObservacao());
            myViewHolder.valorServicoPedido.setText("Valor: "+ NumberFormat.getCurrencyInstance().format(pedidosList.get(position).getId_Proposta().getValor()));
            myViewHolder.position = position;
            myViewHolder.id = pedidosList.get(position).getId();
            myViewHolder.idCliente = pedidosList.get(position).getId_Cliente().getId();
            myViewHolder.idPrestador = pedidosList.get(position).getId_Prestador().getId();
            myViewHolder.ratingBarPedidoCliente.setRating(pedidosList.get(position).getId_Cliente().getAvaliacaoCliente().floatValue());
            myViewHolder.statusPedido.setText("Status: "+pedidosList.get(position).getStatus());
        }else{
            myViewHolder.idPedido.setText(pedidosList.get(position).getId().toString());
            myViewHolder.clientePedido.setText(nome);
            myViewHolder.servicoPedido.setText(pedidosList.get(position).getId_Servico().getNome());
            myViewHolder.valorServicoPedido.setText("Valor: "+NumberFormat.getCurrencyInstance().format(pedidosList.get(position).getId_Servico().getValor()));
            myViewHolder.position = position;
            myViewHolder.id = pedidosList.get(position).getId();
            myViewHolder.idCliente = pedidosList.get(position).getId_Cliente().getId();
            myViewHolder.idPrestador = pedidosList.get(position).getId_Prestador().getId();
            myViewHolder.ratingBarPedidoCliente.setRating(pedidosList.get(position).getId_Cliente().getAvaliacaoCliente().floatValue());
            myViewHolder.statusPedido.setText("Status: "+pedidosList.get(position).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return pedidosList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Pedido pedido = new Pedido();
        public int position;
        public Long id, idCliente, idPrestador;
        TextView idPedido;
        TextView clientePedido;
        TextView servicoPedido;
        TextView valorServicoPedido;
        TextView statusPedido;
        RatingBar ratingBarPedidoCliente;
        OnPedidoListener onPedidoListener;

        public ViewHolder (@NonNull View itemView, OnPedidoListener onPedidoListener){
            super(itemView);
            this.onPedidoListener = onPedidoListener;
            idPedido = itemView.findViewById(R.id.idPedido);
            clientePedido = itemView.findViewById(R.id.ttvNomeClientePedido);
            servicoPedido = itemView.findViewById(R.id.ttvDescServicoPedido);
            valorServicoPedido = itemView.findViewById(R.id.ttvValorServicoPedido);
            statusPedido = itemView.findViewById(R.id.ttvStatusPedido);
            ratingBarPedidoCliente = itemView.findViewById(R.id.ratingBarPedidoCliente);
            itemView.findViewById(R.id.btnAceitaPedido).setOnClickListener(this);
            itemView.findViewById(R.id.btnRecusaPedido).setOnClickListener(this);
            itemView.findViewById(R.id.btnCancelaPedido).setOnClickListener(this);
            itemView.findViewById(R.id.btnVisualizarPedido).setOnClickListener(this);
            itemView.findViewById(R.id.btnFinalizaServico).setOnClickListener(this);

            if (parametro.equals("ENVIADAS")) {
                itemView.findViewById(R.id.btnAceitaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnRecusaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnVisualizarPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnCancelaPedido).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnFinalizaServico).setVisibility(View.INVISIBLE);
                statusPedido.setVisibility(View.INVISIBLE);
            }else if (parametro.equals("RECEBIDAS")) {
                itemView.findViewById(R.id.btnAceitaPedido).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnRecusaPedido).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnVisualizarPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnCancelaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnFinalizaServico).setVisibility(View.INVISIBLE);
                statusPedido.setVisibility(View.INVISIBLE);
            }else if (parametro.equals("PROGRESSO")) {
                itemView.findViewById(R.id.btnAceitaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnRecusaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnVisualizarPedido).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btnCancelaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnFinalizaServico).setVisibility(View.VISIBLE);
                statusPedido.setVisibility(View.INVISIBLE);
            }else if (parametro.equals("FINALIZADO")) {
                itemView.findViewById(R.id.btnAceitaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnRecusaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnVisualizarPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnCancelaPedido).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.btnFinalizaServico).setVisibility(View.INVISIBLE);
                statusPedido.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String text = b.getText().toString();
            if ("Aceitar".equals(text)) {
                onPedidoListener.AceitaPedido(getAdapterPosition(), id);
            }else if ("Recusar".equals(text)){
                onPedidoListener.RecusaPedido(getAdapterPosition(), id);
            }else if ("Cancelar Pedido".equals(text)){
                onPedidoListener.CancelaPedido(getAdapterPosition(), id);
            }else if ("Visualizar".equals(text)){
                onPedidoListener.VisualizarPedido(getAdapterPosition(), id);
            }else if ("Finalizar".equals(text)){
                onPedidoListener.FinalizarPedido(getAdapterPosition(), id, idCliente, idPrestador);
            }
        }
    }

    public interface OnPedidoListener{
        void AceitaPedido (int position, Long id);
        void RecusaPedido (int position, Long id);
        void CancelaPedido (int position, Long id);
        void VisualizarPedido (int position, Long id);
        void FinalizarPedido(int adapterPosition, Long id, Long idCliente, Long idPrestador);
    }
}
