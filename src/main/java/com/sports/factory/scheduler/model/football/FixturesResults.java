package com.sports.factory.scheduler.model.football;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FixturesResults {

    private Team team;
    private ArrayList<Match> matches;
}
