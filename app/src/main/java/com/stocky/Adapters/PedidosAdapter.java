package com.stocky.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stocky.Model.Pedido;
import com.stocky.R;

import java.util.ArrayList;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {

    private final ArrayList<Pedido> pedidos;

    public PedidosAdapter(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_pedido, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String product = pedidos.get(position).getProduto();
        final String quantidade = String.valueOf(pedidos.get(position).getQuantidade());
        holder.nomePedido.setText(product);
        holder.qtdPedido.setText(quantidade);

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomePedido;
        public TextView qtdPedido;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nomePedido = itemView.findViewById(R.id.produtoPedido);
            qtdPedido = itemView.findViewById(R.id.qtdPediido);
        }
    }
}
