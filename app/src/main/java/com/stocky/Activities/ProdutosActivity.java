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
import com.stocky.Adapters.ProductsAdapter;
import com.stocky.Model.Product;
import com.stocky.R;

import java.util.ArrayList;

public class ProdutosActivity extends AppCompatActivity {

    ArrayList<Product> products;
    ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        RecyclerView productList = findViewById(R.id.list_products);

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

                adapter = new ProductsAdapter(products);
                productList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

        novoProduto();
    }

    public void novoProduto() {
        Button btnNewProduto = findViewById(R.id.novoProduto);

        btnNewProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdutosActivity.this, NewProdutoActivity.class);
                startActivity(intent);
            }
        });
    }
}