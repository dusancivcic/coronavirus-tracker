package com.example.coronavirustracker.controllers;

import com.example.coronavirustracker.model.LocationStats;
import com.example.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoronaVirusController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String getHomePage(Model model){
        List<LocationStats> locationStats = coronaVirusDataService.getLocationStatsFinal();
        String totalCasesReported = ("Total cases reported: "+coronaVirusDataService.totalReportedCases());
        String totalCasesReportedToday = ("Total cases reported today: "+coronaVirusDataService.totalReportedCasesToday());
        model.addAttribute("locationStats", locationStats);
        model.addAttribute("totalCasesReported",totalCasesReported);
        model.addAttribute("totalCasesReportedToday",totalCasesReportedToday);
        return "homePage";
    }
}
