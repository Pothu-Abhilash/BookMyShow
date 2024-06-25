package com.acciojob.BookMyShow.Service;

import com.acciojob.BookMyShow.Models.User;
import com.acciojob.BookMyShow.Repository.UserRepository;
import com.acciojob.BookMyShow.Request.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public String addUser(AddUserRequest userRequest) {

        User user = User.builder().userName(userRequest.getName())
                .mailId(userRequest.getEmailId())
                .age(userRequest.getAge())
                .mobileNo(userRequest.getMobileNo())
                .build();



        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userRequest.getEmailId());
        mailMessage.setFrom("springboot970@gmail.com");
        mailMessage.setSubject("Welcome to Book My Show Application !!");

        String body = "Hi "+userRequest.getName()+" !" +
                "Welcome to Book My Show Application, Enjoy WELCOME10 to get 10% off on tickets";
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);

        user = userRepository.save(user);
        return "User has been added to Db with UserId "+user.getUserId();
    }
}
