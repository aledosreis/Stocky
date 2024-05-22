package com.stocky.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stocky.Adapters.StockAdapter;
import com.stocky.Model.Product;
import com.stocky.R;

import java.util.ArrayList;

public class EstoqueActivity extends AppCompatActivity {

    ArrayList<Product> products;
    StockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);

        RecyclerView stockList = findViewById(R.id.list_estoque);

        products = new ArrayList<>();

        DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    products.add(product);
                }

                adapter = new StockAdapter(products);
                stockList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        stockList.setLayoutManager(layoutManager);

        movimentaEstoque();
    }

    public void movimentaEstoque() {
        Button btnEntrada = findViewById(R.id.entrada);
        Button btnSaida = findViewById(R.id.saida);

        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstoqueActivity.this, EntradaEstoqueActivity.class);
                startActivity(intent);
            }
        });

        btnSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstoqueActivity.this, SaidaEstoqueActivity.class);
                startActivity(intent);
            }
        });
    }
}