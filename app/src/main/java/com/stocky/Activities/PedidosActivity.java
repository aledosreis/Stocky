package com.stocky.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stocky.Adapters.PedidosAdapter;
import com.stocky.Model.Pedido;
import com.stocky.Model.Product;
import com.stocky.R;

import java.util.ArrayList;
import java.util.Calendar;

public class PedidosActivity extends AppCompatActivity {

    Spinner spProduto;
    ArrayList<Pedido> pedidos;
    PedidosAdapter adapter;
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        RecyclerView listaPedidos = findViewById(R.id.list_pedidos);
        pedidos = new ArrayList<>();

        adapter = new PedidosAdapter(pedidos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        listaPedidos.setLayoutManager(layoutManager);
        listaPedidos.setAdapter(adapter);

        inicializaSpinner();
        adicionaPedido();
        imprimePedido();
    }

    public void inicializaSpinner() {
        spProduto = findViewById(R.id.spinnerProdutos);
        ArrayList<String> prod = new ArrayList<>();

        DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prod.clear();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    prod.add(product.getDescricao());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PedidosActivity.this, android.R.layout.simple_spinner_dropdown_item, prod);
                spProduto.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void adicionaPedido() {
        Button btnAddPedido = findViewById(R.id.addPedido);
        EditText etQtdPedido = findViewById(R.id.etQtdPedido);

        btnAddPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qtdPedido = etQtdPedido.getText().toString();
                String selected = spProduto.getSelectedItem().toString();

                if (qtdPedido.equals("")) {
                    Toast.makeText(PedidosActivity.this, "Digite uma quantidade maior que 0.", Toast.LENGTH_SHORT).show();
                } else {
                    int qtd = Integer.parseInt(qtdPedido);
                    if (qtd == 0) {
                        Toast.makeText(PedidosActivity.this, "Digite um numero maior que 0", Toast.LENGTH_SHORT).show();
                    } else {
                        Pedido pedido = new Pedido(selected,qtd);
                        pedidos.add(pedido);
                        adapter.notifyItemInserted(pedidos.size());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void imprimePedido() {
        Button btnImprime = findViewById(R.id.printPedido);

        btnImprime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PedidosActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    WebView webView = new WebView(PedidosActivity.this);
                    webView.setWebViewClient(new WebViewClient() {

                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            createWebPrintJob(view);
                            myWebView = null;
                        }
                    });

                    String htmlDoc = getDocument();

                    webView.loadDataWithBaseURL(null, htmlDoc,
                            "text/HTML", "UTF-8", null);

                    myWebView = webView;
                }
            }
        });
    }

    private String getDocument() {

        String[] monthName = {"Janeiro", "Fevereiro",
                "Março", "Abril", "Maio", "Junho", "Julho",
                "Agosto", "Setembro", "Outubro", "Novembro",
                "Dezembro"};

        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];

        String htmlDocument = "<html><head><style>#title {" +
                "      text-align: center;" +
                "      font-family: arial, sans-serif;" +
                "    }" +
                "    #pedidos {" +
                "      text-align: center;" +
                "      font-family: Arial, sans-serif;" +
                "      border-collapse: collapse;" +
                "      border: 3px solid #ddd;" +
                "      width: 100%;" +
                "    }" +
                "    #pedidos td, #pedidos th {" +
                "      border: 1px solid #ddd;" +
                "      padding: 8px;" +
                "    }" +
                "    #pedidos tr:nth-child(even) {" +
                "      background-color: #f2f2f2;" +
                "    }" +
                "    #pedidos th {" +
                "      padding-top: 12px;" +
                "      padding-bottom: 12px;" +
                "      text-align: center;" +
                "      background-color: #4caf50;" +
                "      color: #fff;" +
                "    }</style>";
        htmlDocument += "<body><div><h1 id='title'>Pedidos do mês de " + month + "</h1>";
        htmlDocument += "<table id='pedidos'><tbody><tr><th>Produto</th><th>Qtd</th></tr>";

        for (Pedido pedido : pedidos) {
            String produto = pedido.getProduto();
            String quantidade = String.valueOf(pedido.getQuantidade());
            htmlDocument += "<tr><td>"+ produto +"</td><td>"+ quantidade +"</td></tr>";
        }

        htmlDocument += "</tbody></table></div></body></html>";

        return htmlDocument;
    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("Pedido");

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }
}