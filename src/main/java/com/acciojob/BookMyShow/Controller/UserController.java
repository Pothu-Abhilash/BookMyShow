package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.Request.AddUserRequest;
import com.acciojob.BookMyShow.Response.BookingHistoryResponse;
import com.acciojob.BookMyShow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public String addUser(@RequestBody AddUserRequest userRequest){

        return userService.addUser(userRequest);
    }

    @GetMapping("bookinghistory")
    public ResponseEntity<List<BookingHistoryResponse>> getBookingHistory(@RequestParam("userName") String userName){

        return new ResponseEntity<>(userService.getBookingHistory(userName), HttpStatus.OK);
    }
}
