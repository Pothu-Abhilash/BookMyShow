package com.acciojob.BookMyShow.Response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoviesInTheaterResponse {

    private String movieName;
    private LocalDate showDate;
    private LocalTime showTime;
    private String theaterName;

}
