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
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.R;

import java.util.List;

public class CustomAdapterMensagem extends RecyclerView.Adapter {

    List<Mensagem> mensagemLists;
    Context context;
    private OnMensagemListener mOnMensagemListener;



    public CustomAdapterMensagem(Context context, List<Mensagem> mensagemLists, OnMensagemListener mOnMensagemListener) {
        this.mensagemLists = mensagemLists;
        this.context = context;
        this.mOnMensagemListener = mOnMensagemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_custom_list_message, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, mOnMensagemListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.MensagemId.setText(mensagemLists.get(position).getId().toString());
        myViewHolder.MensagemNome.setText(mensagemLists.get(position).getNome().toString());
        myViewHolder.MensagemDesc.setText(mensagemLists.get(position).getObsMensagem().toString());
        myViewHolder.MensagemValor.setText("Valor: R$"+mensagemLists.get(position).getValor().toString());
        myViewHolder.position = position;
        myViewHolder.id = mensagemLists.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mensagemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Card card = new Card();
        public int position;
        public Long id;
        TextView MensagemId;
        TextView MensagemNome;
        TextView MensagemDesc;
        TextView MensagemValor;
        OnMensagemListener onMensagemListener;

        public ViewHolder (@NonNull View itemView, OnMensagemListener onMensagemListener){
            super(itemView);
            this.onMensagemListener = onMensagemListener;
            MensagemId = itemView.findViewById(R.id.idMensagem);
            MensagemNome = itemView.findViewById(R.id.ttvNomeMensagem);
            MensagemDesc = itemView.findViewById(R.id.ttvDescMensagem);
            MensagemValor = itemView.findViewById(R.id.ttvValorMensagem);
            itemView.findViewById(R.id.btnRemoverMensagem).setOnClickListener(this);
            itemView.findViewById(R.id.btnEditarMensagem).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String text = b.getText().toString();
            switch (text) {
                case "Remover":
                    onMensagemListener.onCardRemoverClick(getAdapterPosition(), id);
                    break;
                case "Editar":
                    onMensagemListener.onCardEditarClick(getAdapterPosition(), id);
                    break;
            }
        }
    }

    public interface OnMensagemListener{
        void onCardRemoverClick (int position, Long id);
        void onCardEditarClick (int position, Long id);

    }
}
