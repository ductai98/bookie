package com.taild.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Data
public class ErrorMessage {
    private String code;

    private String message;

    private HttpStatus httpStatus;
}
