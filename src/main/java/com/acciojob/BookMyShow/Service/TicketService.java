package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Enums.SeatType;
import com.acciojob.BookMyShow.Models.Show;
import com.acciojob.BookMyShow.Models.ShowSeat;
import com.acciojob.BookMyShow.Models.Ticket;
import com.acciojob.BookMyShow.Models.User;
import com.acciojob.BookMyShow.Repository.ShowRepository;
import com.acciojob.BookMyShow.Repository.ShowSeatRepository;
import com.acciojob.BookMyShow.Repository.TicketRepository;
import com.acciojob.BookMyShow.Repository.UserRepository;
import com.acciojob.BookMyShow.Request.BookTicketRequest;
import com.acciojob.BookMyShow.Response.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ShowSeatRepository showSeatRepository;
    public String bookTicket(BookTicketRequest bookTicketRequest) throws Exception{

        //1 get show entity
        Show show = showRepository.findById(bookTicketRequest.getShowId()).get();

        //2 Get User entity

        User user= userRepository.findById(bookTicketRequest.getUserId()).get();

        //Mark those seats and calculate the total amount

        Integer totalAmount = 0;
        List<ShowSeat> showSeatList = show.getShowSeatList();

        for(ShowSeat showSeat : showSeatList)
        {
            String seatNo = showSeat.getSeatNo();

            if(bookTicketRequest.getRequestedSeats().contains(seatNo))
            {
                //checking whether the seat is booked or not
                showSeat.setIsBooked(Boolean.TRUE);

                //calculating the ticket price
                if(showSeat.getSeatType().equals(SeatType.CLASSIC)){
                    totalAmount += 100;
                }else {
                    totalAmount += 150;
                }
            }
        }

        //Create ticket entity and set attributes
        Ticket ticket = Ticket.builder().showTime(show.getShowTime())
                .showDate(show.getShowDate())
                .movieName(show.getMovie().getMovieName())
                .theaterName(show.getTheater().getName())
                .totalAmount(totalAmount)
                .bookedSeat(bookTicketRequest.getRequestedSeats().toString())
                .show(show)
                .user(user)
                .build();


        showSeatRepository.saveAll(showSeatList);
        ticketRepository.save(ticket);

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
                .totalAmount(ticket.getTotalAmount())
                .build();

        return ticketResponse;
    }
}
