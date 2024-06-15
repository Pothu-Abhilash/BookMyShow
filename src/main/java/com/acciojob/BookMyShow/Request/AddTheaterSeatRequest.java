package com.acciojob.BookMyShow.Request;

import lombok.Data;

@Data
public class AddTheaterSeatRequest {
    private Integer theaterId;
    private Integer noOfClassicSeats;
    private Integer noOfPremiumSeats;
}
