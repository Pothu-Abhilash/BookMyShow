package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.Request.AddUserRequest;
import com.acciojob.BookMyShow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public String addUser(@RequestBody AddUserRequest userRequest){

        return userService.addUser(userRequest);
    }
}
