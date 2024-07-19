package com.acciojob.BookMyShow.Mailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void cancelTicket(String email, String theaterName, String movieName, LocalDate showDate, String userName) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("springboot970@gmail.com");
        mailMessage.setSubject("Cancellation Of Ticket");

        String body = String.format("Dear "+userName+",\n\n" +
                "Your ticket for the movie "+movieName+" at "+theaterName+" theater on "+showDate+" has been successfully canceled.\n\n" +
                "Thank you for using our service.\n\n" +
                "Best regards,\n" +
                "BookMyShow Team");

        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }


    public void addUser(String emailId, String name) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailId);
        mailMessage.setFrom("springboot970@gmail.com");
        mailMessage.setSubject("Welcome to Book My Show Application !!");

        String body = "Hi "+name+" !\n\n" +
                "Welcome to Book My Show Application, Enjoy WELCOME10 to get 10% off on tickets\n\nBest regards,\nBookMyShow Team";
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }

    public void bookTicket(String userName, String mailId, LocalDate showDate, LocalTime showTime, String movieName, String theater, String seatNo, Integer totalAmount) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailId);
        mailMessage.setFrom("springboot970@gmail.com");
        mailMessage.setSubject("Booking Conformation");

        String body = "Hi "+userName+",\n\n"+
                "You have successfully booked a ticket for the movie "+movieName+"\n"+
                "Theater: " + theater + "\n" +
                "Show Date: " + showDate + "\n" +
                "Show Time: " + showTime + "\n" +
                "Seat Number: " + seatNo + "\n\n" +
                "Total Amount: "+totalAmount+ "\n\n"+
                "Thank you for booking with us!\n" +
                "BookMyShow";

        mailMessage.setText(body);

        javaMailSender.send(mailMessage);

    }
}
