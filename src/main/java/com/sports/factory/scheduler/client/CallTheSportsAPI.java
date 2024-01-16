package com.sports.factory.scheduler.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CallTheSportsAPI {


    private final RestTemplate restTemplate;

    public List<String> getCricketAPIResults(String cricketAPIKey) {
        List<String> results = new ArrayList<>();
        String indiaScheduleUrl = "https://cricbuzz-cricket.p.rapidapi.com/teams/v1/2/schedule";
        String australiaScheduleUrl = "https://cricbuzz-cricket.p.rapidapi.com/teams/v1/4/schedule";
        String india = makeCricketApiCall(indiaScheduleUrl,cricketAPIKey);
        String australia = makeCricketApiCall(australiaScheduleUrl,cricketAPIKey);
        results.add(india);
        results.add(australia);
        return results;
    }

    public List<String> getFootballAPIResults(String footballAPIKey) {
        List<String> results = new ArrayList<>();
        String footballUrl = "https://football-web-pages1.p.rapidapi.com/fixtures-results.json?team=13";
        String manchesterUnited = makeFootballApiCall(footballUrl,footballAPIKey);
        results.add(manchesterUnited);
        return results;
    }

    private String makeCricketApiCall(String apiUrl, String cricketAPIKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", cricketAPIKey);
        headers.add("X-RapidAPI-Host", "cricbuzz-cricket.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println("Calling the API");
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class
        );
        return response.getBody();
    }

    private String makeFootballApiCall(String apiUrl, String footballAPIKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", footballAPIKey);
        headers.add("X-RapidAPI-Host", "football-web-pages1.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println("Calling the API");
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}
