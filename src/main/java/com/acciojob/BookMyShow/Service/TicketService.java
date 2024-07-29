package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Enums.SeatStatus;
import com.acciojob.BookMyShow.Enums.SeatType;
import com.acciojob.BookMyShow.Mailservice.EmailService;
import com.acciojob.BookMyShow.Models.*;
import com.acciojob.BookMyShow.Repository.*;
import com.acciojob.BookMyShow.Request.BookTicketRequest;
import com.acciojob.BookMyShow.Response.TicketResponse;
import com.acciojob.BookMyShow.CustomException.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private EmailService emailService;

    public String bookTicket(BookTicketRequest bookTicketRequest) {

        //1. Find the Show Entity
        Optional<Show> optionalShow = showRepository.findById(bookTicketRequest.getShowId());
        if(optionalShow.isEmpty()){
            return "You have entered incorrect ShowId, Please enter valid ShowId";
        }
        Show show = optionalShow.get();

        //2. Find the User Entity
        Optional<User> optionalUser = userRepository.findById(bookTicketRequest.getUserId());
        if(optionalUser.isEmpty()){
            return "You have entered incorrect UserId, Please enter valid UserId";
        }
        User user = optionalUser.get();

        // Create a new Ticket
        Integer totalAmount = 0;
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

        //3. Mark those Seats as booked now and calculate total Amount

        List<ShowSeat> showSeatList = show.getShowSeatList();

        List<ShowSeat> bookedSeatList = new ArrayList<>();
        for(ShowSeat showSeat : showSeatList)
        {
            String seatNo = showSeat.getSeatNo();

            if(bookTicketRequest.getRequestedSeats().contains(seatNo))
            {
                if(showSeat.getSeatStatus().equals(SeatStatus.BOOKED)){
                 return "These seats are Already Booked";
               }

                //making seat as booked
                showSeat.setSeatStatus(SeatStatus.BOOKED);
                showSeat.setTicket(ticket);

                if(showSeat.getSeatType().equals(SeatType.CLASSIC)){
//                    System.out.println("classic "+totalAmount);
                    totalAmount = totalAmount + 100;
                }
                else {
//                    System.out.println("preminum"+totalAmount);
                    totalAmount = totalAmount + 150;
                }
                //addfood
                if(bookTicketRequest.getIsFoodAttached()){
                    totalAmount += 100;
                    showSeat.setIsFoodAttached(Boolean.TRUE);
                }
//                showSeatRepository.save(showSeat);
                bookedSeatList.add(showSeat);
            }
        }
        ticket = ticketRepository.save(ticket);
        showSeatRepository.saveAll(showSeatList);
//        ticket.setShowSeats(bookedSeatList);

        //sending conformation mail to user
        emailService.bookTicket(user.getUserName(),user.getMailId(),show.getShowDate(),show.getShowTime(),show.getMovie().getMovieName(),
                                show.getTheater().getName(),bookTicketRequest.getRequestedSeats().toString(),totalAmount);

        //5. save the ticket into DB and return Ticket Entity (Ticket Response)
        return ticket.getTicketId();

    }

    public TicketResponse generateTicket(String ticketId)  {
        if(ticketId.isEmpty() || ticketId == null){
            throw new CustomException("Please enter Valid ticketId");
        }

        Optional<Ticket>optionalTicket = ticketRepository.findById(ticketId);

        if(optionalTicket.isEmpty()){
            throw new CustomException("You have entered in Valid TicketId");
        }
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
        if(theaterName.isEmpty() || theaterName == null){
            throw new CustomException("Please enter valid theater name");
        }
        Optional<Theater>optionalTheater = Optional.ofNullable(theaterRepository.findTheaterByName(theaterName));
        if(optionalTheater.isEmpty()){
            throw new CustomException("Theater does not exists");
        }

        Integer revenue = 0;
       List<Ticket> ticketList = ticketRepository.findAll();

       for (Ticket ticket : ticketList){
           if(ticket.getTheaterName().equals(theaterName)){
               revenue += ticket.getTotalAmount();
           }

       }
       return revenue;
    }

    public Integer getRevenueOfTheaterEachDay(String theaterName, LocalDate date)  {


        if(theaterName.isEmpty() || theaterName == null)
        {
            throw new CustomException("Please Enter valid theater name");
        }
        if(date == null)
        {
            throw new CustomException("Please enter valid theater name");
        }
        Integer revenue = 0;
        List<Ticket> ticketList  =ticketRepository.findAll();

        for (Ticket ticket : ticketList){
            if(theaterName.equals(ticket.getTheaterName()) || date.isEqual(ticket.getShowDate()) ){
                revenue += ticket.getTotalAmount();
            }
        }
        return revenue;
    }

    @Transactional
    public String cancelTicket(String ticketId) {

        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);


        if(optionalTicket.isPresent()){

            Ticket ticket = optionalTicket.get();
            User user = ticket.getUser();
            String email = user.getMailId();

//            List<ShowSeat> showSeatList = new ArrayList<>();

            //updating the seat status
            List<ShowSeat> showSeats = ticket.getShowSeats();
            if (showSeats.isEmpty()) {
                return "No seats found for the ticket";
            }
            for(ShowSeat seat : showSeats){
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setTicket(null);
            }
                showSeatRepository.saveAll(showSeats);

            //Deleting the ticket
            ticketRepository.deleteById(ticketId);

            //sending mail to user
            emailService.cancelTicket(email, ticket.getTheaterName(), ticket.getMovieName(), ticket.getShowDate(),user.getUserName());


            return "Your Ticket has been canceled, Conformation message has been sent to your mail";
        }

        return "You have entered Invalid Ticket Id";
    }
}
