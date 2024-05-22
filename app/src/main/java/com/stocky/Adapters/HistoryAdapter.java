package com.stocky.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.stocky.Model.History;
import com.stocky.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final ArrayList<History> histories;

    public HistoryAdapter(ArrayList<History> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_historico, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String product = histories.get(position).getProduct();
        final String qtdEstoque = String.valueOf(histories.get(position).getQtd());
        final String dateStockChange = histories.get(position).getDate();
        final String tipoTransacao = histories.get(position).getTipoTransacao();

        holder.nomeProduto.setText(product);
        holder.qtdProduto.setText(qtdEstoque);
        holder.dateChange.setText(dateStockChange);

        if (tipoTransacao.equals("entrada")) {
            holder.nomeProduto.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
            holder.qtdProduto.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
            holder.dateChange.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
        } else if (tipoTransacao.equals("saida")){
            holder.nomeProduto.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
            holder.qtdProduto.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
            holder.dateChange.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeProduto;
        public TextView qtdProduto;
        public TextView dateChange;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nomeProduto = itemView.findViewById(R.id.nomeProduto);
            qtdProduto = itemView.findViewById(R.id.qtdEstoque);
            dateChange = itemView.findViewById(R.id.dateChamge);
        }
    }
}
