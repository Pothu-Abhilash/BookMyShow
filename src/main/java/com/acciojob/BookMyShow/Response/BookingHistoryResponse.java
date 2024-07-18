package com.acciojob.BookMyShow.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingHistoryResponse {

    private String movieName;
    private String theaterName;
    private LocalDate bookedDate;
    private LocalDate showDate;
    private LocalTime showTime;
    private String bookedSeat;
}
