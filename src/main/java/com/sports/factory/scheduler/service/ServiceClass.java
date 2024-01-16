package com.sports.factory.scheduler.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.factory.scheduler.client.CallTheSportsAPI;
import com.sports.factory.scheduler.model.cricket.CricketResponse;
import com.sports.factory.scheduler.model.cricket.TeamMatchesDatum;
import com.sports.factory.scheduler.model.football.FootballResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceClass {

    private final CallTheSportsAPI callTheSportsAPI;

    @Cacheable("cricketResults")
    public CricketResponse getDataFromAPI(String cricketAPIKey) throws IOException {
        CricketResponse cricketResponse = new CricketResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        List<String> cricketAPIResults = callTheSportsAPI.getCricketAPIResults(cricketAPIKey);
        String india = cricketAPIResults.get(0);
        String australia = cricketAPIResults.get(1);
        CricketResponse cricketResponseForIND = objectMapper.readValue(india, CricketResponse.class);
        ArrayList<TeamMatchesDatum> teamMatchesDataIND = cricketResponseForIND.getTeamMatchesData();
        CricketResponse cricketResponseForAUS = objectMapper.readValue(australia, CricketResponse.class);
        ArrayList<TeamMatchesDatum> teamMatchesDataAUS = cricketResponseForAUS.getTeamMatchesData();
        ArrayList<TeamMatchesDatum> combinedList = new ArrayList<>(teamMatchesDataIND);
        combinedList.addAll(teamMatchesDataAUS);
        cricketResponse.setTeamMatchesData(combinedList);
        return cricketResponse;
    }


    @Cacheable("footBallResults")
    public FootballResponse getFootballDataFromAPI(String footballAPIKey) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        List<String> footballAPIResults = callTheSportsAPI.getFootballAPIResults(footballAPIKey);
        String manchesterUnited = footballAPIResults.get(0);
        return objectMapper.readValue(manchesterUnited, FootballResponse.class);
    }
}
