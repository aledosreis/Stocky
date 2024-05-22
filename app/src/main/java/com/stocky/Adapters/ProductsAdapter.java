package com.stocky.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stocky.Model.Product;
import com.stocky.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private final ArrayList<Product> products;

    public ProductsAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_produto, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String product = products.get(position).getDescricao();
        holder.nomeProduto.setText(product);
        holder.nomeProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product productToUpdateName = products.get(position);
                EditText textNewProductName = new EditText(v.getContext());
                textNewProductName.setHint("Digite o nome do produto");
                textNewProductName.setPadding(16, 16, 16, 16);
                new AlertDialog.Builder(v.getContext())
                        .setTitle("ALTERAR PRODUTO")
                        .setMessage("Alterar nome do produto " + productToUpdateName.getDescricao())
                        .setCancelable(false)
                        .setView(textNewProductName)
                        .setPositiveButton("SALVAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = productToUpdateName.getId();
                                String descricao = productToUpdateName.getDescricao();
                                String newProductText = textNewProductName.getText().toString();
                                HashMap hashMapProductDesc = new HashMap();
                                HashMap hashMapHistoryProd = new HashMap();
                                hashMapProductDesc.put("descricao", newProductText);
                                DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("products").child(id);
                                DatabaseReference databaseHistory = FirebaseDatabase.getInstance().getReference("history");
                                Query historyQuery = databaseHistory.orderByChild("product").equalTo(descricao);
                                databaseProducts.updateChildren(hashMapProductDesc).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        historyQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot historySnapshot : snapshot.getChildren()) {
                                                    hashMapHistoryProd.put(historySnapshot.getKey()+"/product", newProductText);

                                                }
                                                databaseHistory.updateChildren(hashMapHistoryProd);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });
                            }
                        })
                        .setNegativeButton("CANCELAR", null).show();
            }
        });
        holder.btnRemoveProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product productToDelete = products.get(position);

                new AlertDialog.Builder(v.getContext())
                        .setTitle("REMOVER PRODUTO")
                        .setMessage("Tem certeza que deseja remover o produto " + productToDelete.getDescricao() + "?")
                        .setCancelable(false)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String id = productToDelete.getId();
                                String descricao = productToDelete.getDescricao();

                                DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("products").child(id);
                                DatabaseReference databaseHistory = FirebaseDatabase.getInstance().getReference("history");

                                Query historyQuery = databaseHistory.orderByChild("product").equalTo(descricao);

                                databaseProducts.removeValue();

                                historyQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot historySnapshot : snapshot.getChildren()) {
                                            historySnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                Toast.makeText(holder.btnRemoveProd.getContext(), "O produto foi removido.", Toast.LENGTH_SHORT).show();
                            } }).setNegativeButton("NÃO", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeProduto;
        public View layout;
        public Button btnRemoveProd;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nomeProduto = itemView.findViewById(R.id.nomeProduto);
            btnRemoveProd = itemView.findViewById(R.id.btnRemoveProd);
        }
    }
}
