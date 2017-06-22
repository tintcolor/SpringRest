package com.apress.repository;

import com.apress.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anthonyjones on 6/21/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
