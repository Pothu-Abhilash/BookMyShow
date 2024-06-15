package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findMovieBymovieName(String movieName);
}
