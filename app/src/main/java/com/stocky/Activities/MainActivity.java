package com.stocky.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stocky.R;

public class MainActivity extends AppCompatActivity {

    private float currentVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            currentVersion = Float.parseFloat(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SharedPreferences preferences = getSharedPreferences("STOCKY", Context.MODE_PRIVATE);
        float lastVersionChangelog = preferences.getFloat("lastVersionChangelog", 1.0f);

        System.out.println(currentVersion);
        System.out.println(lastVersionChangelog);

        if (lastVersionChangelog != currentVersion) {
            SharedPreferences preferences1 = getSharedPreferences("STOCKY", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences1.edit();
            editor.putFloat("lastVersionChangelog", currentVersion);
            editor.apply();
            changelogDialog();
        }
        transicaoTelas();
    }

    private void transicaoTelas() {
        Button btnProdutos = findViewById(R.id.btnProduto);
        Button btnEstoque = findViewById(R.id.btnEstoque);
        Button btnHistorico = findViewById(R.id.btnHistorico);
        Button btnPedidos = findViewById(R.id.btnPedidos);

        btnProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProdutosActivity.class);
                startActivity(intent);
            }
        });

        btnEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EstoqueActivity.class);
                startActivity(intent);
            }
        });

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoricoActivity.class);
                startActivity(intent);
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PedidosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void changelogDialog() {
        String changelogMessage = "- Adicionado informação de modificações da atualização," +
                "\n- Adicionado a possibilidade de alterar o nome de um produto," +
                "\n- Corrigido erro no titulo da confirmação de remoção de um produto," +
                "\n- Desabilitado a rotação de tela do app.";
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Notas de Atualização")
                .setIcon(R.drawable.ic_baseline_new_releases_24)
                .setMessage(changelogMessage)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .show();
    }
}