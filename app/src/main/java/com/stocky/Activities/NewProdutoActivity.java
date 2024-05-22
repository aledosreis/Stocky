package com.stocky.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stocky.Model.Product;
import com.stocky.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_produto);

        Button btnAddProduto = findViewById(R.id.btnAddProduto);

        btnAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaProduto();
            }
        });
    }

    public void adicionaProduto() {

        DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("products");

        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String today = date.format(mCalendar.getTime());

        EditText etNovoProduto = findViewById(R.id.etNewProduto);
        String prod = etNovoProduto.getText().toString();
        if (!TextUtils.isEmpty(prod)) {
            String productId = databaseProducts.push().getKey();
            Product product = new Product(productId, prod, 0, today);
            databaseProducts.child(productId).setValue(product);
            Toast.makeText(this, "Produto cadastrado", Toast.LENGTH_LONG).show();
            finish();
        } else {
            new AlertDialog.Builder(NewProdutoActivity.this)
                    .setTitle("ERRO!")
                    .setMessage("Digite o nome do produto.")
                    .setPositiveButton("OK", null).show();
        }
    }
}