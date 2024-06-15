package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Models.Movie;
import com.acciojob.BookMyShow.Repository.MovieRepository;
import com.acciojob.BookMyShow.Request.AddMovieRequest;
import com.acciojob.BookMyShow.Request.UpdateMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    public String addMovie(AddMovieRequest movieRequest) {
        Movie movie = new Movie();

        movie.setMovieName(movieRequest.getMovieName());
        movie.setDuration(movieRequest.getDuration());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setRating(movieRequest.getRatings());
        movie.setReleaseDate(movieRequest.getReleaseDate());

        movieRepository.save(movie);

        return "Movie has been saved to db with movieId "+movie.getMovieId();
    }

    public String updateMovie(UpdateMovieRequest updateMovieRequest) {

        //Get Movie entity
        Movie movie = movieRepository.findMovieBymovieName(updateMovieRequest.getMovieName());

        movie.setLanguage(updateMovieRequest.getLanguage());
        movie.setRating(updateMovieRequest.getNewRating());

        movieRepository.save(movie);
        return "Movie has been updated";
    }
}
