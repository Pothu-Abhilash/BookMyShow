package com.acciojob.BookMyShow.Request;

import lombok.Data;

import java.util.List;

@Data
public class BookTicketRequest {

    private List<String> requestedSeats;
    private Integer showId;
    private Integer userId;
    private Boolean isFoodAttached;
}
