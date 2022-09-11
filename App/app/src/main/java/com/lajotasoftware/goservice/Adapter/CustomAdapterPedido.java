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

import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.R;

import java.util.List;

public class CustomAdapterPedido extends RecyclerView.Adapter {

    List<Pedido> pedidosList;
    Context context;
    private OnPedidoListener mOnPedidoListener;

    public CustomAdapterPedido(Context context, List<Pedido> pedidosList, OnPedidoListener mOnPedidoListener) {
        this.pedidosList = pedidosList;
        this.context = context;
        this.mOnPedidoListener = mOnPedidoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_pedido, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mOnPedidoListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.idPedido.setText(pedidosList.get(position).getId().toString());
        myViewHolder.clientePedido.setText(pedidosList.get(position).getId_Cliente().getPrimeiroNome());
        myViewHolder.servicoPedido.setText(pedidosList.get(position).getId_Servico().getNome());
        myViewHolder.valorServicoPedido.setText("Valor: R$"+pedidosList.get(position).getId_Servico().getValor().toString());
        myViewHolder.position = position;
        myViewHolder.id = pedidosList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return pedidosList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Card card = new Card();
        public int position;
        public Long id;
        TextView idPedido;
        TextView clientePedido;
        TextView servicoPedido;
        TextView valorServicoPedido;
        OnPedidoListener onPedidoListener;

        public ViewHolder (@NonNull View itemView, OnPedidoListener onPedidoListener){
            super(itemView);
            this.onPedidoListener = onPedidoListener;
            idPedido = itemView.findViewById(R.id.idPedido);
            clientePedido = itemView.findViewById(R.id.ttvNomeClientePedido);
            servicoPedido = itemView.findViewById(R.id.ttvDescServicoPedido);
            valorServicoPedido = itemView.findViewById(R.id.ttvValorServicoPedido);
            itemView.findViewById(R.id.btnAceitaPedido).setOnClickListener(this);
            itemView.findViewById(R.id.btnRecusaPedido).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String text = b.getText().toString();
            if ("Solicitar".equals(text)) {
                onPedidoListener.AceitaPedido(getAdapterPosition(), id);
            }else if ("Recusar".equals(text)){
                onPedidoListener.RecusaPedido(getAdapterPosition(), id);
            }
        }
    }

    public interface OnPedidoListener{
        void AceitaPedido (int position, Long id);
        void RecusaPedido (int position, Long id);
    }
}
