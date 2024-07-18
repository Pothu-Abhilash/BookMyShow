package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {

   Theater findTheaterByName(String name);
}
