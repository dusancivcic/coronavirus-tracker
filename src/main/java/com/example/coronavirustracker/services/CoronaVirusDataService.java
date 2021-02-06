package com.example.coronavirustracker.services;

import com.example.coronavirustracker.model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> locationStatsFinal = new ArrayList<>();

    public List<LocationStats> getLocationStatsFinal() {
        return locationStatsFinal;
    }

    public Integer totalReportedCases(){
        Integer totalReportedCasesNumber = locationStatsFinal.stream().mapToInt(cases -> cases.getLatestTotalCases()).sum();
        return totalReportedCasesNumber;
    }

    public Integer totalReportedCasesToday(){
        Integer totalReportedCasesToday = locationStatsFinal.stream().mapToInt(cases -> cases.getDifferenceInCases()).sum();
        return  totalReportedCasesToday;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getVirusData() throws IOException, InterruptedException {
        List<LocationStats> locationStatsTemp = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        StringReader stringReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            if(record.get("Province/State").equals("")){
                locationStats.setState("No state ");
            }else{
                locationStats.setState(record.get("Province/State"));
            }
            locationStats.setCountry(record.get("Country/Region"));
            Integer latestTotalCases = Integer.parseInt(record.get(record.size()-1));
            Integer dayBeforeLatestTotalCases = Integer.parseInt(record.get(record.size()-2));
            locationStats.setLatestTotalCases(latestTotalCases);
            locationStats.setDifferenceInCases(latestTotalCases-dayBeforeLatestTotalCases);
            locationStatsTemp.add(locationStats);
        }
        this.locationStatsFinal = locationStatsTemp;
    }
}
