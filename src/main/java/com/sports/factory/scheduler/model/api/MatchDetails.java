package com.sports.factory.scheduler.model.api;

import lombok.Data;

@Data
public class MatchDetails {

    private String match;
    private String seriesName;
    private String matchDesc;
    private String matchFormat;
    private String startDate;
    private String endDate;
    private String city;
}
