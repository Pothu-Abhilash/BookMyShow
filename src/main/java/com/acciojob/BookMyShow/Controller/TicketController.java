package com.acciojob.BookMyShow.Controller;

import com.acciojob.BookMyShow.Request.BookTicketRequest;
import com.acciojob.BookMyShow.Response.TicketResponse;
import com.acciojob.BookMyShow.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("Ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("bookTicket")
    public String bookTicket(@RequestBody BookTicketRequest bookTicketRequest) throws Exception {

        return ticketService.bookTicket(bookTicketRequest);
    }

    @GetMapping("generateTicket")
    public TicketResponse generateTicket(@RequestParam("ticketId") String ticketId) {

        return ticketService.generateTicket(ticketId);
    }

    @DeleteMapping("cancelTicket")
    public String cancelTicket(@RequestParam String ticketId) throws Exception
    {
        return ticketService.cancelTicket(ticketId);
    }

    @GetMapping("gettotalrevenue")
    public ResponseEntity getTotalRevenue(){
        return new ResponseEntity<>(ticketService.getTotalRevenue(), HttpStatus.OK);
    }

    @GetMapping("getrevenuoftheater")
    public ResponseEntity getRevenueOfTheater(@RequestParam("theaterName") String theaterName){
        return new ResponseEntity<>(ticketService.getRevenueOfTheater(theaterName),HttpStatus.OK);
    }

    @GetMapping("get-revenue-of-theater-eachday")
    public ResponseEntity getRevenueOfTheaterEachDay(@RequestParam("theaterName")String theaterName,
                                                     @RequestParam("Date")LocalDate date){
        return new ResponseEntity<>(ticketService.getRevenueOfTheaterEachDay(theaterName,date),HttpStatus.OK);
    }
}
