package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.Request.AddShowRequest;
import com.acciojob.BookMyShow.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("add")
    public ResponseEntity addShow(@RequestBody AddShowRequest showRequest){

        String response = showService.addShow(showRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}