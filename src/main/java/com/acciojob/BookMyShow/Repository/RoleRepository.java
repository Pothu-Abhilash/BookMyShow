package com.acciojob.BookMyShow.Repository;

import com.acciojob.BookMyShow.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
//    Role findByuserName(String userName);

//    Role findByuserName(String username);

    Role findByrole(String userName);

//    Role findByUserName(String UserName);
}
