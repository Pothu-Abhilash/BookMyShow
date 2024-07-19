package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Mailservice.EmailService;
import com.acciojob.BookMyShow.Models.Ticket;
import com.acciojob.BookMyShow.Models.User;
import com.acciojob.BookMyShow.Repository.TicketRepository;
import com.acciojob.BookMyShow.Repository.UserRepository;
import com.acciojob.BookMyShow.Request.AddUserRequest;
import com.acciojob.BookMyShow.Response.BookingHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    public String addUser(AddUserRequest userRequest) {

        User user = User.builder().userName(userRequest.getName())
                .mailId(userRequest.getEmailId())
                .age(userRequest.getAge())
                .mobileNo(userRequest.getMobileNo())
                .build();


        emailService.addUser(userRequest.getEmailId(),userRequest.getName());

        user = userRepository.save(user);
        return "User has been added to Db with UserId "+user.getUserId();
    }

    public List<BookingHistoryResponse> getBookingHistory(String userName) {



        List<BookingHistoryResponse> bookingHistoryResponseList = new ArrayList<>();

      List<Ticket>ticketList = ticketRepository.findAll();

      for(Ticket ticket : ticketList){
          if(ticket.getUser().getUserName().equals(userName)){
              BookingHistoryResponse bookingHistoryResponse = new BookingHistoryResponse().builder()
                      .movieName(ticket.getMovieName())
                      .bookedSeat(ticket.getBookedSeat())
                      .theaterName(ticket.getTheaterName())
                      .bookedDate(ticket.getBookedDate())
                      .showDate(ticket.getShowDate())
                      .showTime(ticket.getShowTime())
                      .build();

              bookingHistoryResponseList.add(bookingHistoryResponse);
          }
      }
        return bookingHistoryResponseList;
    }
}
