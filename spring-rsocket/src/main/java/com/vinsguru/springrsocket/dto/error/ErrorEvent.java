package com.vinsguru.springrsocket.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEvent {

    private StatusCode statusCode;
    private final LocalDate date = LocalDate.now();

}
