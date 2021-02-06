package com.example.coronavirustracker.model;

public class LocationStats {

    private String state;
    private String country;
    private Integer latestTotalCases;
    private Integer differenceInCases;

    public Integer getDifferenceInCases() {
        return differenceInCases;
    }

    public void setDifferenceInCases(Integer differenceInCases) {
        this.differenceInCases = differenceInCases;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatestTotalCases(Integer latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Integer getLatestTotalCases() {
        return latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}
