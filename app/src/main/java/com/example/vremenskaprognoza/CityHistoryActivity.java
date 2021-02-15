package com.example.vremenskaprognoza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CityHistoryActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener{

    private ArrayList<City> listCities = new ArrayList<>();
    private RecyclerView recyclerViewCityHistory;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_history);

        recyclerViewCityHistory  = (RecyclerView) findViewById(R.id.recyclerViewCityHistory);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewCityHistory.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(listCities, this);
        recyclerViewCityHistory.setAdapter(mAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("cities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    City city = new City(postSnapshot.getValue(City.class).date, postSnapshot.getValue(City.class).time, postSnapshot.getValue(City.class).cityName);
                    listCities.add(city);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CityHistoryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
            });
    }


    @Override
    public void onItemClick(int position) {
        Intent oMainIntent = new Intent(getApplicationContext(), MainActivity.class);
        City city = listCities.get(position);
        oMainIntent.putExtra("citySelected", city.cityName);

        startActivity(oMainIntent);
    }
}