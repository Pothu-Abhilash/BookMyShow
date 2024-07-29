package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByuserName(String username);

    User findBymailId(String mailId);

    Optional<User> findBymobileNo(String mobileNo);


}
