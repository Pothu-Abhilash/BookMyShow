package com.acciojob.BookMyShow.Request;

import com.acciojob.BookMyShow.Enums.Language;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddMovieRequest {

    private String movieName;
    private Double duration;
    private LocalDate releaseDate;
    private Language language;
    private Double ratings;

}
