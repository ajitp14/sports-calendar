package com.sports.factory.scheduler.model.football;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Match {

    private String date;
    private String venue;
    private Competition competition;
    private String time;
    @JsonProperty("home-team")
    private TeamsDetails homeTeam;
    @JsonProperty("away-team")
    private TeamsDetails awayTeam;
}
