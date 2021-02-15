package com.example.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerCities;
    private EditText cityInput;
    private Button buttonConfirm;
    private Button buttonInput;

    private String sCityName;
    private String apiKey = "820a34e17d9d98cd5b29d24f15ec32fe";
    private String lang = "hr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        spinnerCities = (Spinner) findViewById(R.id.spinnerCities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(adapter);
        spinnerCities.setOnItemSelectedListener(this);

        cityInput = (EditText) findViewById(R.id.editTextCityName);

        buttonConfirm = (Button) findViewById(R.id.button);
        buttonInput= (Button) findViewById(R.id.buttonInput);


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                oMainIntent.putExtra("citySelected", sCityName);

                startActivity(oMainIntent);
            }
        });

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sCityName = cityInput.getText().toString();
               String sCityNameTest = sCityName.replaceAll(" ", "");
               if(!sCityNameTest.equals(""))
                   {
                       sCityName = sCityName.trim();
                       sCityName = sCityName.replaceAll(" +", " ");

                       ApiManager.getInstance().service().getForecast(sCityName, apiKey, lang).enqueue(new Callback<Forecast>() {
                           @Override
                           public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                               if(response.body() != null && response.body().Weather.get(0).getDescription() != null)
                                   {
                                       Intent oMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                       oMainIntent.putExtra("citySelected", sCityName);

                                       startActivity(oMainIntent);
                                   }
                               else
                                   {
                                       Toast.makeText(SelectCityActivity.this, "Nevaljani unos grada!", Toast.LENGTH_LONG).show();
                                   }
                           }

                           @Override
                           public void onFailure(Call<Forecast> call, Throwable t) {
                               t.printStackTrace();
                           }
                       });
                   }
               else
                   {
                       Toast.makeText(SelectCityActivity.this, "Niste unijeli grad!", Toast.LENGTH_LONG).show();
                   }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sCityName = spinnerCities.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}