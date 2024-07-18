package com.acciojob.BookMyShow.Request;

import com.acciojob.BookMyShow.Enums.Genre;
import com.acciojob.BookMyShow.Enums.Language;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddMovieRequest {

    private String movieName;
    private Genre genre;
    private Double duration;
    private LocalDate releaseDate;
    private Language language;
    private Double ratings;

}
