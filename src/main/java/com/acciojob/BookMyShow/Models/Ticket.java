package com.acciojob.BookMyShow.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketId;

    private String bookedSeat;

    private LocalDate showDate;

    private LocalTime showTime;

    private String movieName;

    private String theaterName;

    private Integer totalAmount;
    private LocalDate bookedDate;

    @JoinColumn
    @ManyToOne
    private Show show;

    @JoinColumn
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "ticket",fetch = FetchType.EAGER)
    private List<ShowSeat> showSeats = new ArrayList<>();
}
