package com.stocky.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stocky.Model.Product;
import com.stocky.R;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private final ArrayList<Product> products;

    public StockAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_estoque, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String product = products.get(position).getDescricao();
        final String qtdEstoque = String.valueOf(products.get(position).getQtdEstoque());
        final String dateStockChange = products.get(position).getDt_alteracao();

        holder.nomeProduto.setText(product);
        holder.qtdProduto.setText(qtdEstoque);
        holder.dateChange.setText(dateStockChange);
    }

    @Override
    public int getItemCount() {
        return products.size();
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
