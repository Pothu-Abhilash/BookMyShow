package com.acciojob.BookMyShow.Request;

import lombok.Data;
import lombok.Locked;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddShowRequest {

    private LocalDate showDate;
    private LocalTime showTime;
    private String movieName;
    private Integer theaterId;
}
