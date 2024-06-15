package com.acciojob.BookMyShow.Repository;


import com.acciojob.BookMyShow.Models.TheaterSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterSeatsRepository extends JpaRepository<TheaterSeat, Integer> {
}
