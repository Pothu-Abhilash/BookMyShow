package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.Request.AddMovieRequest;
import com.acciojob.BookMyShow.Request.UpdateMovieRequest;
import com.acciojob.BookMyShow.Response.MoviesInTheaterResponse;
import com.acciojob.BookMyShow.Response.RecommendMovies;
import com.acciojob.BookMyShow.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("add")
    public ResponseEntity addMovie(@RequestBody AddMovieRequest movieRequest){

        String response = movieService.addMovie(movieRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity updateMovieAttributes(@RequestBody UpdateMovieRequest updateMovieRequest){

        String response = movieService.updateMovie(updateMovieRequest);
        return new ResponseEntity(response,HttpStatus.OK);
    }

    @GetMapping("getMoviePresentInTheaters")
    public  ResponseEntity<List<MoviesInTheaterResponse>> getMoviesPresentInTheater(@RequestParam("movieName") String movieName)  {

            List<MoviesInTheaterResponse> moviesInTheaterResponseList = movieService.getMoviesPresentInTheater(movieName);
            return new ResponseEntity<>( moviesInTheaterResponseList,HttpStatus.OK);


    }

    @GetMapping("recommandMovies")
    public ResponseEntity<List<RecommendMovies>> recommandMovies(){

            return new ResponseEntity<>(movieService.recommandMovies(),HttpStatus.OK);

    }
}