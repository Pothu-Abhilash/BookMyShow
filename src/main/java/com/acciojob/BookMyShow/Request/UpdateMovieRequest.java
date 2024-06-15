package com.acciojob.BookMyShow.Request;

import com.acciojob.BookMyShow.Enums.Language;
import lombok.Data;

@Data
public class UpdateMovieRequest {
    private String movieName;
    private Language language;
    private Double newRating;
}
