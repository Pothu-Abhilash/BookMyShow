package com.acciojob.BookMyShow.Request;

import lombok.Data;

@Data
public class AddUserRequest {

    private String userName;
    private Integer age;
    private String emailId;
    private String mobileNo;
    private String password;
}
