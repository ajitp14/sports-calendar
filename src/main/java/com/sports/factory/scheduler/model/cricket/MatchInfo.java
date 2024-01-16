package com.sports.factory.scheduler.model.cricket;

import lombok.Data;

@Data
public class MatchInfo {


    private String seriesName;
    private String matchDesc;
    private String matchFormat;
    private String startDate;
    private String endDate;
    private Team team1;
    private Team team2;
    private VenueInfo venueInfo;
}
