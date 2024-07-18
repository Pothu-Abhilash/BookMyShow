package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Models.Movie;
import com.acciojob.BookMyShow.Models.Show;
import com.acciojob.BookMyShow.Repository.MovieRepository;
import com.acciojob.BookMyShow.Repository.ShowRepository;
import com.acciojob.BookMyShow.Request.AddMovieRequest;
import com.acciojob.BookMyShow.Request.UpdateMovieRequest;
import com.acciojob.BookMyShow.Response.RecommendMovies;
import com.acciojob.BookMyShow.Response.ShowListResponse;
import com.acciojob.BookMyShow.Response.MoviesInTheaterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowService showService;


    public String addMovie(AddMovieRequest movieRequest) {
        Movie movie = new Movie();

        movie.setMovieName(movieRequest.getMovieName());
        movie.setGenre(movieRequest.getGenre());
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

    public List<MoviesInTheaterResponse> getMoviesPresentInTheater(String movieName) {

        // get movie by name
        Movie movie = movieRepository.findMovieByMovieName(movieName);

        // find showlist
        ShowListResponse showListResponse = showService.getShowList();
        List<Show> showList = showListResponse.getShowList();
        // iterate shows in theater
        List<MoviesInTheaterResponse> responseList = new ArrayList<>();

        for (Show show : showList) {
            if (show.getMovie().equals(movie)) {
                MoviesInTheaterResponse theatersResponse = MoviesInTheaterResponse.builder()
                        .movieName(show.getMovie().getMovieName())
                        .showTime(show.getShowTime())
                        .showDate(show.getShowDate())
                        .theaterName(show.getTheater().getName())
                        .build();
                responseList.add(theatersResponse);
            }
        }
        return responseList;
    }

    public List<RecommendMovies> recommandMovies() {

        List<RecommendMovies> recommandMoviesList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        List<Show> showList = showRepository.findAll();

        for(Show show : showList){
            System.out.println(show.getShowDate());

            if(show.getShowDate().isBefore(currentDate))
            {
                RecommendMovies recommendMovies = new RecommendMovies().builder()
                        .movieName(show.getMovie().getMovieName())
                        .genre(show.getMovie().getGenre())
                        .ratings(show.getMovie().getRating())
                        .showDate(show.getShowDate())
                        .showTime(show.getShowTime())
                        .theaterName(show.getTheater().getName()).build();

                //add to arraylist
                recommandMoviesList.add(recommendMovies);
            }
        }

        //adding unique movies

        List<RecommendMovies> uniqueMovies = new ArrayList<>();

        for(RecommendMovies recommandMovie : recommandMoviesList) {
            boolean isDefault = false;
            for(RecommendMovies uniqueMovie : uniqueMovies){
                String duplicateMovie = recommandMovie.getMovieName();

                if(duplicateMovie.equals(uniqueMovie.getMovieName())){
                    isDefault = true;
                    break;
                }
            }
            if(!isDefault){
                uniqueMovies.add(recommandMovie);
            }
        }
        return uniqueMovies;
    }
}
