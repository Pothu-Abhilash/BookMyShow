package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show,Integer> {
}
