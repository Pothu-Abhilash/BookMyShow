package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Enums.SeatStatus;
import com.acciojob.BookMyShow.Models.*;
import com.acciojob.BookMyShow.Repository.MovieRepository;
import com.acciojob.BookMyShow.Repository.ShowRepository;
import com.acciojob.BookMyShow.Repository.ShowSeatRepository;
import com.acciojob.BookMyShow.Repository.TheaterRepository;
import com.acciojob.BookMyShow.Request.AddShowRequest;
import com.acciojob.BookMyShow.Response.ShowListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    private  ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public String addShow(AddShowRequest showRequest) {

        Movie movie = movieRepository.findMovieByMovieName(showRequest.getMovieName());
       Theater theater = theaterRepository.findById(showRequest.getTheaterId()).get();

        //Add validations on if movie and theater are valid scenarios
        Show show = Show.builder()
                .showDate(showRequest.getShowDate())
                .showTime(showRequest.getShowTime())
                .movie(movie)
                .theater(theater)
                .build();

        show = showRepository.save(show);


        //Associate the corresponding show Seats along with it
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();


        List<ShowSeat> showSeatList = new ArrayList<>();

        for(TheaterSeat theaterSeat : theaterSeatList) {

            ShowSeat showSeat = ShowSeat.builder().seatNo(theaterSeat.getSeatNo())
                    .seatType(theaterSeat.getSeatType())
                    .seatStatus(SeatStatus.AVAILABLE)
                    .isFoodAttached(Boolean.FALSE)
                    .show(show)
                    .build();
            showSeatList.add(showSeat);
        }

        //Setting the bidirectional mapping attribute
        show.setShowSeatList(showSeatList);

        showSeatRepository.saveAll(showSeatList);
        return "The show has been saved to the DB with showId "+show.getShowId();
    }

    public ShowListResponse getShowList(){

        ShowListResponse showListResponse = ShowListResponse.builder().showList(showRepository.findAll()).build();
        return showListResponse;
    }


    public List<String> availableSeats(Integer showId) {

//        Show show = showRepository.findById(showId).get();

        List<ShowSeat> seatList = showSeatRepository.findAll();

        List<String> showSeatList = new ArrayList<>();

        for(ShowSeat seat : seatList){

            SeatStatus seatStatus = seat.getSeatStatus();
            //checking for showId same or not
            if(seat.getShow().getShowId().equals(showId))
            {
                //checking for available seats in show
                if(seatStatus.isAvailable() == true){
                    showSeatList.add(seat.getSeatNo());
                }
            }

        }
        return showSeatList;
    }
}
