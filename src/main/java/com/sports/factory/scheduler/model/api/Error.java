package com.sports.factory.scheduler.model.api;

import lombok.Data;

@Data
public class Error {

    private String code;
    private String message;
    private String details;
}
