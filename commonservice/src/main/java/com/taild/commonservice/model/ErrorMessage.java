package com.taild.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorMessage {
    private String code;

    private String message;

    private HttpStatus httpStatus;
}
