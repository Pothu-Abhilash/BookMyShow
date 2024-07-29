package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.CustomException.CustomException;
import com.acciojob.BookMyShow.Mailservice.EmailService;
import com.acciojob.BookMyShow.Models.Ticket;
import com.acciojob.BookMyShow.Models.User;
import com.acciojob.BookMyShow.Repository.RoleRepository;
import com.acciojob.BookMyShow.Repository.TicketRepository;
import com.acciojob.BookMyShow.Repository.UserRepository;
import com.acciojob.BookMyShow.Request.AddUserRequest;
import com.acciojob.BookMyShow.Response.BookingHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private RoleRepository roleRepository;


    public String addUser(AddUserRequest userRequest) {

        if(userRequest.getUserName() == null || userRequest.getEmailId() == null || userRequest.getMobileNo() == null
                ||userRequest.getAge() ==null || userRequest.getPassword() == null){
            return "Please Enter all fileds";
        }

        //to take unique email & mobile no
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findBymailId(userRequest.getEmailId()));

        if(optionalUser.isPresent()){
            throw new CustomException("MailId already exist in db");
        }

        Optional<User> userOptional = userRepository.findBymobileNo(userRequest.getMobileNo());

        if(userOptional.isPresent()){
            throw new CustomException("Mobile number is already exists");
        }

        //roles
//        Role role = roleRepository.findByrole(userRequest.getUserName());
//            roleRepository.save(role);

        //to bcrypt the password
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

        User user = User.builder().userName(userRequest.getUserName())
                .mailId(userRequest.getEmailId())
                .age(userRequest.getAge())
                .mobileNo(userRequest.getMobileNo())
                .password(encoder.encode(userRequest.getPassword()))
                .build();


        emailService.addUser(userRequest.getEmailId(),userRequest.getUserName());

        user = userRepository.save(user);
        return "User has been added to Db with UserId "+user.getUserId();
    }

    public List<BookingHistoryResponse> getBookingHistory(String userName) {

        if(userName == null){
            throw new CustomException("Please enter valid UserName");
        }

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByuserName(userName));
        if(optionalUser.isEmpty()){
            throw new CustomException(userName+" is not present in DB");
        }

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
