package com.stocky.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stocky.Adapters.HistoryAdapter;
import com.stocky.Model.History;
import com.stocky.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HistoricoActivity extends AppCompatActivity {

    ArrayList<History> histories;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        mCalendar.add(Calendar.DATE, -30);
        Date lastMonth = null;
        try {
            lastMonth = date.parse(date.format(mCalendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RecyclerView historyList = findViewById(R.id.list_history);

        histories = new ArrayList<>();

        DatabaseReference databaseHistory = FirebaseDatabase.getInstance().getReference("history");
        Date finalLastMonth = lastMonth;
        databaseHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                histories.clear();

                for (DataSnapshot historySnapshot : snapshot.getChildren()) {
                    History history = historySnapshot.getValue(History.class);
                    Date productDate = null;
                    try {
                        productDate = date.parse(history.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (productDate.after(finalLastMonth)) {
                        histories.add(history);
                    }
                }

                Collections.reverse(histories);

                adapter = new HistoryAdapter(histories);
                historyList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyList.setLayoutManager(layoutManager);
    }
}