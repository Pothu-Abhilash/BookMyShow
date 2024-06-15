package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
}
