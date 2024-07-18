package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.Request.AddShowRequest;
import com.acciojob.BookMyShow.Response.ShowListResponse;
import com.acciojob.BookMyShow.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("add")
    public ResponseEntity addShow(@RequestBody AddShowRequest showRequest){

        try {
            String response = showService.addShow(showRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-showsList")
    public ShowListResponse getShowsList(){
        return showService.getShowList();
    }

}
