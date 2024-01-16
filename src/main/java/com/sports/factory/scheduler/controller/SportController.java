package com.sports.factory.scheduler.controller;


import com.sports.factory.scheduler.mapper.ResponseMapper;
import com.sports.factory.scheduler.model.api.SportResponse;
import com.sports.factory.scheduler.model.cricket.CricketResponse;
import com.sports.factory.scheduler.model.football.FootballResponse;
import com.sports.factory.scheduler.service.ServiceClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@Validated
@RequiredArgsConstructor
public class SportController {

    private final ServiceClass serviceClass;

    private final ResponseMapper responseMapper;

    @GetMapping("/get/schedule")
    public ResponseEntity<SportResponse> getData(@NotBlank @RequestHeader String cricketAPIKey,
                                                 @NotBlank @RequestHeader String footballAPIKey) throws IOException {
        CricketResponse cricketResponse = serviceClass.getDataFromAPI(cricketAPIKey);
        FootballResponse footballDataFromAPI = serviceClass.getFootballDataFromAPI(footballAPIKey);
        SportResponse sportResponse = responseMapper.mapScheduleResponse(cricketResponse,footballDataFromAPI);
        return ResponseEntity.ok(sportResponse);
    }

}
