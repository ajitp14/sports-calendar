package com.sports.factory.scheduler.model.football;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FootballResponse {

    @JsonProperty("fixtures-results")
    private FixturesResults fixturesResults;
}
