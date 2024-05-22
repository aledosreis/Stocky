package com.stocky.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stocky.Model.History;
import com.stocky.Model.Product;
import com.stocky.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SaidaEstoqueActivity extends AppCompatActivity {

    Spinner spProduto;
    ArrayList<Product> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saida_estoque);
        Button btnRetiraProd = findViewById(R.id.btnRetiraEstoque);

        inicializaSpinner();

        btnRetiraProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saidaEstoque();
            }
        });
    }

    public void inicializaSpinner() {
        produtos = new ArrayList<>();
        spProduto = findViewById(R.id.spRetiraEstoque);
        ArrayList<String> prod = new ArrayList<>();

        DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prod.clear();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    produtos.add(product);
                    prod.add(product.getDescricao());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SaidaEstoqueActivity.this, android.R.layout.simple_spinner_dropdown_item, prod);
                spProduto.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saidaEstoque() {
        EditText etQuantidade = findViewById(R.id.etQtdRemoveEstoque);

        String qtdEntrada = etQuantidade.getText().toString();
        int selected = spProduto.getSelectedItemPosition();
        Product selectedProduct = produtos.get(selected);

        String id = selectedProduct.getId();
        String descicao = selectedProduct.getDescricao();

        DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("products").child(id);
        DatabaseReference databaseHistory = FirebaseDatabase.getInstance().getReference("history");

        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String today = date.format(mCalendar.getTime());

        if (!TextUtils.isEmpty(qtdEntrada) && !TextUtils.equals(qtdEntrada, "0")) {
            int qtd = Integer.parseInt(qtdEntrada);
            int newQtd = selectedProduct.getQtdEstoque()-qtd;

            if (newQtd >= 0) {
                Product product = new Product(id, descicao, newQtd, today);
                History history = new History(descicao, qtd, today, "saida");
                databaseProducts.setValue(product);
                String historyId = databaseHistory.push().getKey();
                databaseHistory.child(historyId).setValue(history);
                finish();
            }
            else {
                new AlertDialog.Builder(SaidaEstoqueActivity.this)
                        .setTitle("ERRO!")
                        .setMessage("Não é possível retirar uma quantidade maior do que a disponível.")
                        .setPositiveButton("OK", null).show();
            }

        } else {
            new AlertDialog.Builder(SaidaEstoqueActivity.this)
                    .setTitle("ERRO!")
                    .setMessage("Digite um valor diferente de 0.")
                    .setPositiveButton("OK", null).show();
        }
    }
}