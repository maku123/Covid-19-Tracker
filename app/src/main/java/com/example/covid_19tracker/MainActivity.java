package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19tracker.api.ApiUtilities;
import com.example.covid_19tracker.api.CountryDataModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView countryName;
    private TextView totalRecovered;
    private TextView todayRecovered;
    private TextView totalConfirm;
    private TextView todayConfirm;
    private TextView active;
    private TextView totalDeath;
    private TextView todayDeath;
    private TextView totalTests;
    private TextView updatedOnText;
    private ImageView refreshBTN;
    private List<CountryDataModel> list;
    private String country="India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryName=findViewById(R.id.countryName);
        totalRecovered=findViewById(R.id.totalRecovered);
        todayRecovered=findViewById(R.id.todayRecovered);
        totalConfirm=findViewById(R.id.totalConfirm);
        todayConfirm=findViewById(R.id.todayConfirm);
        active=findViewById(R.id.active);
        totalDeath=findViewById(R.id.totalDeath);
        todayDeath=findViewById(R.id.todayDeath);
        totalTests=findViewById(R.id.totalTests);
        refreshBTN=findViewById(R.id.refreshBTN);
        updatedOnText=findViewById(R.id.updatedOnText);

        if(getIntent().getStringExtra("CountryName")!=null){
            country=getIntent().getStringExtra("CountryName");
        }

        update();

        refreshBTN.setOnClickListener(v -> {
            refreshBTN.animate().rotationBy(360).setDuration(500);
            update();
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        });

        countryName.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,CountryListActivity.class));
        });


    }

    private void update() {
        list=new ArrayList<>();
        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<CountryDataModel>>() {
            @Override
            public void onResponse(Call<List<CountryDataModel>> call, Response<List<CountryDataModel>> response) {
                list.addAll(response.body());

                for(int i=0;i<list.size();i++){
                    if(list.get(i).getCountry().equals(country)){
                        countryName.setText(country);
                        totalRecovered.setText(NumberFormat.getInstance().format(list.get(i).getRecovered()));
                        todayRecovered.setText("+"+NumberFormat.getInstance().format(list.get(i).getTodayRecovered()));
                        todayConfirm.setText("+"+NumberFormat.getInstance().format(list.get(i).getTodayCases()));
                        totalConfirm.setText(NumberFormat.getInstance().format(list.get(i).getCases()));
                        active.setText(NumberFormat.getInstance().format(list.get(i).getActive()));
                        totalDeath.setText(NumberFormat.getInstance().format(list.get(i).getDeaths()));
                        todayDeath.setText("+"+NumberFormat.getInstance().format(list.get(i).getTodayDeaths()));
                        totalTests.setText(NumberFormat.getInstance().format(list.get(i).getTests()));
                        updateDate(list.get(i).getUpdated());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDate(long updated) {
        DateFormat format=new SimpleDateFormat("MMM dd, yyyy   hh:mm aa");

        long millisecond=updated;
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);

        updatedOnText.setText("updated on : "+format.format(calendar.getTime()));

    }
}