package com.sports.factory.scheduler.model.api;

import lombok.Data;

import java.util.List;
@Data
public class SportResponse {

    private List<MatchDetails> schedule;
}


