package com.acciojob.BookMyShow.Request;

import lombok.Data;

@Data
public class AddTheaterRequest {

    private Integer noOfScreens;
    private String name;
    private String address;
}
