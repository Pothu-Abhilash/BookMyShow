package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.Request.AddTheaterRequest;
import com.acciojob.BookMyShow.Request.AddTheaterSeatRequest;
import com.acciojob.BookMyShow.Response.TheaterMovies;
import com.acciojob.BookMyShow.Service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("addTheater")
    public ResponseEntity addTheater(@RequestBody AddTheaterRequest theaterRequest){

        String response = theaterService.addTheater(theaterRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("associateSeats")
    public ResponseEntity associateSeats(@RequestBody AddTheaterSeatRequest theaterSeatRequest){

        String response = theaterService.associateSeats(theaterSeatRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("theatermovielist")
    public ResponseEntity<List<TheaterMovies>> getTheaterMovieList(@RequestParam("theaterName")String theaterName)  {
        return new ResponseEntity<>(theaterService.getTheaterMovieList(theaterName),HttpStatus.OK);
    }

}
