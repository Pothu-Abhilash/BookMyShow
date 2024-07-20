package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Enums.SeatType;
import com.acciojob.BookMyShow.Models.Show;
import com.acciojob.BookMyShow.Models.Theater;
import com.acciojob.BookMyShow.Models.TheaterSeat;
import com.acciojob.BookMyShow.Repository.TheaterRepository;
import com.acciojob.BookMyShow.Repository.TheaterSeatsRepository;
import com.acciojob.BookMyShow.Request.AddTheaterRequest;
import com.acciojob.BookMyShow.Request.AddTheaterSeatRequest;
import com.acciojob.BookMyShow.Response.ShowListResponse;
import com.acciojob.BookMyShow.Response.TheaterMovies;
import com.acciojob.BookMyShow.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private TheaterSeatsRepository theaterSeatsRepository;

@Autowired
private ShowService showService;

    public String addTheater(AddTheaterRequest theaterRequest) {

        if(theaterRequest.getName() == null || theaterRequest.getAddress() ==null ||
            theaterRequest.getNoOfScreens() == null){
            throw new CustomException("Please enter all fileds");
        }

        //checking theater is present in db
        List<Theater> theaterList = theaterRepository.findAll();
        for(Theater theater : theaterList){
            if(!theater.getName().equals(theaterRequest.getName())){
                throw new CustomException(theater.getName()+" is not present in DB");
            }
        }

       Theater theater = Theater.builder().noOfScreens(theaterRequest.getNoOfScreens())
                        .name(theaterRequest.getName())
                        .address(theaterRequest.getAddress())
                        .build();

        theaterRepository.save(theater);
        return "Theater has been saved to db with theaterId "+theater.getTheaterId();
    }

    public String associateSeats(AddTheaterSeatRequest theaterSeatRequest) {

        if(theaterSeatRequest.getTheaterId() == null || theaterSeatRequest.getNoOfClassicSeats() == null ||
        theaterSeatRequest.getNoOfPremiumSeats() == null){
            throw new CustomException("Please enter all fields");
        }

        int theaterId = theaterSeatRequest.getTheaterId();
        int noOfClassicSeats = theaterSeatRequest.getNoOfClassicSeats();
        int noOfPremiumSeats = theaterSeatRequest.getNoOfPremiumSeats();

        List<TheaterSeat> theaterSeatList = new ArrayList<>();

        //1. Get the theaterEntity from DB
        Optional<Theater> optionalTheater = theaterRepository.findById(theaterId);

        if(optionalTheater.isEmpty()){
            throw new CustomException("Theater id "+theaterId+" is Invalid");
        }

        Theater theater = optionalTheater.get();

        //2. Generate those seatNos through for Classic Seats

        int noOfRowsOfClassicSeats = noOfClassicSeats/5; //Complete rows that i can build
        int noOfSeatsInLastRowClassic = noOfClassicSeats%5;
        int row;
        for(row= 1; row<=noOfRowsOfClassicSeats; row++) {

            for(int j=1;j<=5;j++) {

                char ch = (char)('A'+j-1);
                String seatNo = "" + row + ch;

                TheaterSeat theaterSeat = TheaterSeat.builder().seatNo(seatNo)
                        .seatType(SeatType.CLASSIC)
                        .theater(theater)
                        .build();
                theaterSeatList.add(theaterSeat);
            }
        }

        //For the last row
        for(int j=1;j<=noOfSeatsInLastRowClassic;j++) {
            char ch = (char)('A'+j-1);
            String seatNo = "" + row + ch;
            TheaterSeat theaterSeat = TheaterSeat.builder().seatNo(seatNo)
                    .seatType(SeatType.CLASSIC)
                    .theater(theater)
                    .build();
            theaterSeatList.add(theaterSeat);
        }

        //Same logic for the premium seats
        int noOfRowsInPremiumSeats = noOfPremiumSeats/5;
        int noOfSeatsInLastRowPremium = noOfPremiumSeats%5;

        int currentRow = row;
        if(noOfSeatsInLastRowClassic>0){
            currentRow++;
        }
        for(row=currentRow;row<=noOfRowsInPremiumSeats+currentRow-1; row++) {
            for(int j=1;j<=5;j++) {
                char ch = (char)('A'+j-1);
                String seatNo = "" + row + ch;
                TheaterSeat theaterSeat = TheaterSeat.builder().seatNo(seatNo)
                        .seatType(SeatType.PREMIUM)
                        .theater(theater)
                        .build();
                theaterSeatList.add(theaterSeat);
            }
        }
        //For the last row
        for(int j=1;j<=noOfSeatsInLastRowPremium;j++){
            char ch = (char)('A'+j-1);
            String seatNo = "" + row + ch;
            TheaterSeat theaterSeat = TheaterSeat.builder().seatNo(seatNo)
                    .seatType(SeatType.PREMIUM)
                    .theater(theater)
                    .build();
            theaterSeatList.add(theaterSeat);
        }

        theater.setTheaterSeatList(theaterSeatList);
        theaterRepository.save(theater);

        //Save all the generated Theater seats into the DB
        theaterSeatsRepository.saveAll(theaterSeatList);
        return "The theater seats have been associated";
    }


    public List<TheaterMovies> getTheaterMovieList(String theaterName)  {


        if(theaterName.isEmpty() || theaterName == null){
            throw new CustomException("Please enter valid theater name");
        }
        //find movies present in theater
        ShowListResponse showListResponse = showService.getShowList();
        List<Show> showList = showListResponse.getShowList();

        //iterate shows in theater
        List<TheaterMovies> moviesInTheaterResponseList = new ArrayList<>();
        for(Show show : showList)
        {
            if(show.getTheater().getName().equals(theaterName)){
                TheaterMovies theaterMovies = new TheaterMovies().builder()
                        .movieName(show.getMovie().getMovieName())
                        .showDate(show.getShowDate())
                        .showTime(show.getShowTime())
                        .theaterName(show.getTheater().getName())
                        .build();

                //Add to list
                moviesInTheaterResponseList.add(theaterMovies);
            }
            throw new CustomException("Theater does not exists");
        }
        return  moviesInTheaterResponseList;
    }
}
