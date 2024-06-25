package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,String> {
}
