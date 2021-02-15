package com.example.vremenskaprognoza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity {

    private Button cityHistory;
    private Button citySelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        cityHistory = (Button) findViewById(R.id.btnCityHistory);
        citySelect = (Button) findViewById(R.id.btnCitySelect);



        cityHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oHistoryIntent = new Intent(getApplicationContext(), CityHistoryActivity.class);
                startActivity(oHistoryIntent);
            }
        });

        citySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oSelectIntent = new Intent(getApplicationContext(), SelectCityActivity.class);
                startActivity(oSelectIntent);
            }
        });
    }
}