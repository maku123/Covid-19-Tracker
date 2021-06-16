package com.example.covid_19tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19tracker.api.CountryDataModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {
    private Context context;
    private List<CountryDataModel> list;

    public CountryListAdapter(Context context, List<CountryDataModel> list) {
        this.context = context;
        this.list = list;
    }

    public void filter(List<CountryDataModel> filterList){
        list=filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CountryListAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_country_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CountryListAdapter.ViewHolder holder, int position) {

        CountryDataModel countryDataModel= list.get(position);

        holder.cName.setText(countryDataModel.getCountry());

        holder.itemView.setOnClickListener(v -> {
            Intent intent =new Intent(context,MainActivity.class);
            intent.putExtra("CountryName",countryDataModel.getCountry());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView cName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cName=itemView.findViewById(R.id.cName);

        }
    }
}
