package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Enums.SeatStatus;
import com.acciojob.BookMyShow.Enums.SeatType;
import com.acciojob.BookMyShow.Models.Show;
import com.acciojob.BookMyShow.Models.ShowSeat;
import com.acciojob.BookMyShow.Models.Ticket;
import com.acciojob.BookMyShow.Models.User;
import com.acciojob.BookMyShow.Repository.*;
import com.acciojob.BookMyShow.Request.BookTicketRequest;
import com.acciojob.BookMyShow.Response.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public String bookTicket(BookTicketRequest bookTicketRequest) throws Exception{

        //1. Find the Show Entity
        Show show = showRepository.findById(bookTicketRequest.getShowId()).get();

        //2. Find the User Entity
        User user = userRepository.findById(bookTicketRequest.getUserId()).get();

        //3. Mark those Seats as booked now and calculate total Amount
        Integer totalAmount = 0;
        List<ShowSeat> showSeatList = show.getShowSeatList();

        for(ShowSeat showSeat : showSeatList) {

            String seatNo = showSeat.getSeatNo();
//
            if(bookTicketRequest.getRequestedSeats().contains(seatNo)) {
                if(showSeat.getSeatStatus().equals(SeatStatus.BOOKED)){
                 return "These seats are Already Booked";
               }

                showSeat.setSeatStatus(SeatStatus.BOOKED);

                if(showSeat.getSeatType().equals(SeatType.CLASSIC)){
                    System.out.println("classic "+totalAmount);
                    totalAmount = totalAmount + 100;
                }
                else {
                    System.out.println("preminum"+totalAmount);
                    totalAmount = totalAmount + 150;
                }
                //addfood
                if(bookTicketRequest.getIsFoodAttached()){
                    totalAmount += 100;
                    showSeat.setIsFoodAttached(Boolean.TRUE);
                }
            }
        }

        //4. Create the Ticket Entity and set the attributes
        Ticket ticket = Ticket.builder().showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .movieName(show.getMovie().getMovieName())
                .theaterName(show.getTheater().getName())
                .bookedDate(LocalDate.now())
                .totalAmount(totalAmount)
                .bookedSeat(bookTicketRequest.getRequestedSeats().toString())
                .show(show)
                .user(user)
                .build();


        showSeatRepository.saveAll(showSeatList);
        ticket = ticketRepository.save(ticket);
        //5. save the ticket into DB and return Ticket Entity (Ticket Response)
        return ticket.getTicketId();

    }

    public TicketResponse generateTicket(String ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId).get();

        TicketResponse ticketResponse =TicketResponse.builder().bookedSeats(ticket.getBookedSeat())
                .theaterName(ticket.getTheaterName())
                .showDate(ticket.getShowDate())
                .movieName(ticket.getMovieName())
                .showTime(ticket.getShowTime())
                .bookedDate(LocalDate.now())
                .totalAmount(ticket.getTotalAmount())
                .build();

        return ticketResponse;
    }

    public Integer getTotalRevenue() {

        Integer totalRevenue = 0;
        List<Ticket> ticketList =ticketRepository.findAll();
        for(Ticket ticket : ticketList)
        {
            totalRevenue += ticket.getTotalAmount();
        }
        return totalRevenue;
    }


    public Integer getRevenueOfTheater(String theaterName) {

        Integer revenue = 0;
       List<Ticket> ticketList = ticketRepository.findAll();

       for (Ticket ticket : ticketList){
           if(ticket.getTheaterName().equals(theaterName)){
               revenue += ticket.getTotalAmount();
           }
       }
       return revenue;
    }

    public Integer getRevenueOfTheaterEachDay(String theaterName, LocalDate date) {

        Integer revenue = 0;
        List<Ticket> ticketList  =ticketRepository.findAll();

        for (Ticket ticket : ticketList){
            if(theaterName.equals(ticket.getTheaterName()) || date.isEqual(ticket.getShowDate()) ){
                revenue += ticket.getTotalAmount();
            }
        }
        return revenue;
    }
}
