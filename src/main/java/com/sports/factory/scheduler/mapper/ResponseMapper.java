package com.sports.factory.scheduler.mapper;

import com.sports.factory.scheduler.model.api.MatchDetails;
import com.sports.factory.scheduler.model.api.SportResponse;
import com.sports.factory.scheduler.model.cricket.*;
import com.sports.factory.scheduler.model.football.FootballResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResponseMapper {
    SportResponse sportResponse = new SportResponse();
    List<MatchDetails> matchDetails = new ArrayList<>();
    public SportResponse mapScheduleResponse(CricketResponse cricketResponse, FootballResponse footballResponse) {
        if(cricketResponse!=null) {
            ArrayList<TeamMatchesDatum> teamMatchesData = cricketResponse.getTeamMatchesData();
            teamMatchesData.forEach(teamMatchesDatum -> {
                MatchDetailsMap matchDetailsMap = teamMatchesDatum.getMatchDetailsMap();
                if (matchDetailsMap != null) {
                    ArrayList<Match> match = matchDetailsMap.getMatch();
                    match.forEach(mathDetailsFromResponse -> {
                        MatchDetails cricketSchedule = new MatchDetails();
                        MatchInfo matchInfo = mathDetailsFromResponse.getMatchInfo();
                        cricketSchedule.setMatch(matchInfo.getTeam1().getTeamName() + " Vs "
                                + matchInfo.getTeam2().getTeamName());
                        cricketSchedule.setSeriesName(matchInfo.getSeriesName());
                        cricketSchedule.setMatchDesc(matchInfo.getMatchDesc());
                        cricketSchedule.setMatchFormat(matchInfo.getMatchFormat());
                        String startDate = convertDateToLocalDate(matchInfo.getStartDate());
                        String endDate = convertDateToLocalDate(matchInfo.getEndDate());
                        cricketSchedule.setStartDate(startDate);
                        cricketSchedule.setEndDate(endDate);
                        cricketSchedule.setCity(matchInfo.getVenueInfo().getCity());
                        matchDetails.add(cricketSchedule);
                    });
                }
            });
        }
        if(footballResponse!=null) {
            ArrayList<com.sports.factory.scheduler.model.football.Match> footballMatches = footballResponse.getFixturesResults().getMatches();
            footballMatches.forEach(match -> {
                MatchDetails footballSchedule = new MatchDetails();
                footballSchedule.setMatch(match.getHomeTeam().getName() + " Vs " + match.getAwayTeam().getName());
                footballSchedule.setSeriesName(match.getCompetition().getName());
                footballSchedule.setMatchDesc(null);
                footballSchedule.setMatchFormat("FOOTBALL");
                String kickOff = convertFootballDateToLocalDate(match.getDate(), match.getTime());
                footballSchedule.setStartDate(kickOff);
                footballSchedule.setEndDate(null);
                footballSchedule.setCity(match.getVenue());
                matchDetails.add(footballSchedule);
            });
        }
        matchDetails.sort((match1, match2) -> {
            LocalDateTime date1 = parseStartDate(match1.getStartDate());
            LocalDateTime date2 = parseStartDate(match2.getStartDate());
            return date1.compareTo(date2);
        });
        List<MatchDetails> filteredList = matchDetails.stream().filter(matchDetails -> LocalDateTime.parse(matchDetails.getStartDate()).isAfter(LocalDateTime.now())
                || LocalDateTime.parse(matchDetails.getStartDate()).isEqual(LocalDateTime.now())).collect(Collectors.toList());
        sportResponse.setSchedule(filteredList);
        return sportResponse;
    }

    private LocalDateTime parseStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(startDate, formatter);
    }

    private String convertDateToLocalDate(String date){
        long longValue = Long.parseLong(date);
        Instant instant = Instant.ofEpochMilli(longValue);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toString();
    }

    private String convertFootballDateToLocalDate(String date, String time){
        LocalDate matchDate = LocalDate.parse(date);
        LocalTime matchTime = LocalTime.parse(time);
        LocalDateTime dateTime = LocalDateTime.of(matchDate, matchTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return dateTime.format(formatter);
    }

}
