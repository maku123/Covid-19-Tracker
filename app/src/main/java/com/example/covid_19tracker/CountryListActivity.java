package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covid_19tracker.api.ApiUtilities;
import com.example.covid_19tracker.api.CountryDataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CountryListAdapter countryListAdapter;
    private List<CountryDataModel> list;
    private ProgressDialog dialog;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        list=new ArrayList<>();

        searchBar=findViewById(R.id.searchBar);

        recyclerView=findViewById(R.id.countries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        countryListAdapter=new CountryListAdapter(this,list);
        recyclerView.setAdapter(countryListAdapter);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<CountryDataModel>>() {
            @Override
            public void onResponse(Call<List<CountryDataModel>> call, Response<List<CountryDataModel>> response) {
                list.addAll(response.body());
                countryListAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
                Toast.makeText(CountryListActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<CountryDataModel> filterList=new ArrayList<>();
                for(CountryDataModel filterCountry:list){
                    if(filterCountry.getCountry().toLowerCase().contains(s.toString().toLowerCase())){
                        filterList.add(filterCountry);
                    }
                }
                countryListAdapter.filter(filterList);
            }
        });

    }
}