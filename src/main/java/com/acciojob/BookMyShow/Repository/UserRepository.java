package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
