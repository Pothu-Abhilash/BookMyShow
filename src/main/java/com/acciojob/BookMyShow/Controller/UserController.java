package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.JWT.JwtService;
import com.acciojob.BookMyShow.Request.AddUserRequest;
import com.acciojob.BookMyShow.Request.LoginRequest;
import com.acciojob.BookMyShow.Response.BookingHistoryResponse;
import com.acciojob.BookMyShow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("addUser")
    public String addUser(@RequestBody AddUserRequest userRequest){

        return userService.addUser(userRequest);
    }

    @PostMapping("login")
    public String login(@RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()){

            return jwtService.generateToken(loginRequest.getUserName());
        }
        else {
            return "Failure";
        }
    }


    @GetMapping("bookinghistory")
    public ResponseEntity<List<BookingHistoryResponse>> getBookingHistory(@RequestParam("userName") String userName){

        return new ResponseEntity<>(userService.getBookingHistory(userName), HttpStatus.OK);
    }
}
