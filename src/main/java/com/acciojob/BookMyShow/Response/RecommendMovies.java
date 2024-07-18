package com.acciojob.BookMyShow.Response;

import com.acciojob.BookMyShow.Enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendMovies {
    private String movieName;
    private String theaterName;
    private LocalDate showDate;
    private LocalTime showTime;
    private Double ratings;
    private Genre genre;
}
