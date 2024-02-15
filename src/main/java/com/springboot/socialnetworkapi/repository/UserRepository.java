package com.springboot.socialnetworkapi.repository;


import com.springboot.socialnetworkapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE users.name LIKE %?1%", nativeQuery = true)
    Page<User> getUserByUsername(String name, Pageable pageable);
}
