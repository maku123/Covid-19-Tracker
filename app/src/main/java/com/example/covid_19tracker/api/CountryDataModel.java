package com.example.covid_19tracker.api;

import java.util.Map;

public class CountryDataModel {
    private String country;
    private long updated;
    private long cases;
    private long todayCases;
    private long deaths;
    private long todayDeaths;
    private long recovered;
    private long todayRecovered;
    private long active;
    private long tests;

    public CountryDataModel(String country, long updated, long cases, long todayCases, long deaths,
                            long todayDeaths, long recovered, long todayRecovered, long active,
                            long tests) {
        this.country = country;
        this.updated = updated;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.tests = tests;
    }

    public String getCountry() {
        return country;
    }

    public long getUpdated() {
        return updated;
    }

    public long getCases() {
        return cases;
    }

    public long getTodayCases() {
        return todayCases;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getTodayDeaths() {
        return todayDeaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public long getTodayRecovered() {
        return todayRecovered;
    }

    public long getActive() {
        return active;
    }

    public long getTests() {
        return tests;
    }
}
